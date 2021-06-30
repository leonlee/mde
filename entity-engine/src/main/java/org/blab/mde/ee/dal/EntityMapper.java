package org.blab.mde.ee.dal;

import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.RowMapperFactory;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.ColumnNameMatcher;
import org.jdbi.v3.core.mapper.reflect.ReflectionMappers;
import org.jdbi.v3.core.statement.StatementContext;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.util.AnnotationUtil;
import org.blab.mde.ee.util.EntityUtil;

public class EntityMapper<T> implements RowMapper<T> {
  /**
   * Returns a mapper factory that maps to the given bean class
   *
   * @param type the mapped class
   * @return a mapper factory that maps to the given bean class
   */
  public static RowMapperFactory factory(Class<?> type) {
    return RowMapperFactory.of(type, EntityMapper.of(type));
  }

  /**
   * Returns a mapper factory that maps to the given bean class
   *
   * @param type   the mapped class
   * @param prefix the column name prefix for each mapped bean property
   * @return a mapper factory that maps to the given bean class
   */
  public static RowMapperFactory factory(Class<?> type, String prefix) {
    return RowMapperFactory.of(type, EntityMapper.of(type, prefix));
  }

  /**
   * Returns a mapper for the given bean class
   *
   * @param type the mapped class
   * @return a mapper for the given bean class
   */
  public static <T> RowMapper<T> of(Class<T> type) {
    return EntityMapper.of(type, DEFAULT_PREFIX);
  }

  /**
   * Returns a mapper for the given bean class
   *
   * @param type   the mapped class
   * @param prefix the column name prefix for each mapped bean property
   * @return a mapper for the given bean class
   */
  public static <T> RowMapper<T> of(Class<T> type, String prefix) {
    return new EntityMapper<>(type, prefix);
  }

  static final String DEFAULT_PREFIX = "";

  private final Class<T> type;
  private final String typeName;
  private final String prefix;
  private final BeanInfo info;
  private final ConcurrentMap<String, Optional<PropertyDescriptor>> descriptorByColumnCache = new ConcurrentHashMap<>();

  EntityMapper(Class<T> type, String prefix) {
    this.type = type;
    this.typeName = extractTypeName(type);
    this.prefix = prefix;

    try {
      Object instance = EntityUtil.createOf(type);
      info = Introspector.getBeanInfo(instance.getClass(), Introspector.USE_ALL_BEANINFO);
    } catch (IntrospectionException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String extractTypeName(Class<T> type) {
    Optional<Annotation> annotation = Arrays.stream(type.getDeclaredAnnotations())
        .filter(it -> it.annotationType().getDeclaredAnnotation(CompositeType.class) != null)
        .findFirst();
    if (annotation.isPresent()) {
      return (String) AnnotationUtil.getValue(annotation.get());
    }
    return null;
  }

  @Override
  public T map(ResultSet rs, StatementContext ctx) throws SQLException {
    return specialize(rs, ctx).map(rs, ctx);
  }

  @Override
  public RowMapper<T> specialize(ResultSet rs, StatementContext ctx) throws SQLException {
    List<Integer> columnNumbers = new ArrayList<>();
    List<ColumnMapper<?>> mappers = new ArrayList<>();
    List<PropertyDescriptor> properties = new ArrayList<>();

    ResultSetMetaData metadata = rs.getMetaData();
    List<ColumnNameMatcher> columnNameMatchers = ctx.getConfig(ReflectionMappers.class).getColumnNameMatchers();

    for (int i = 1; i <= metadata.getColumnCount(); ++i) {
      String name = metadata.getColumnLabel(i);

      if (prefix.length() > 0) {
        if (name.length() > prefix.length() &&
            name.regionMatches(true, 0, prefix, 0, prefix.length())) {
          name = name.substring(prefix.length());
        } else {
          continue;
        }
      }

      final Optional<PropertyDescriptor> maybeDescriptor =
          descriptorByColumnCache.computeIfAbsent(name, n -> descriptorForColumn(n, columnNameMatchers));

      if (!maybeDescriptor.isPresent()) {
        continue;
      }

      final PropertyDescriptor descriptor = maybeDescriptor.get();
      final Type type = descriptor.getReadMethod().getGenericReturnType();
      final ColumnMapper<?> mapper = ctx.findColumnMapperFor(type)
          .orElse((r, n, c) -> r.getObject(n));

      columnNumbers.add(i);
      mappers.add(mapper);
      properties.add(descriptor);
    }

    if (columnNumbers.isEmpty() && metadata.getColumnCount() > 0) {
      throw new IllegalArgumentException(String.format("Mapping bean type %s " +
          "didn't find any matching columns in result set", type));
    }

    if (ctx.getConfig(ReflectionMappers.class).isStrictMatching() &&
        columnNumbers.size() != metadata.getColumnCount()) {
      throw new IllegalArgumentException(String.format("Mapping bean type %s " +
              "only matched properties for %s of %s columns", type,
          columnNumbers.size(), metadata.getColumnCount()));
    }

    return (r, c) -> {
      T bean;
      try {
        bean = EntityUtil.createOf(type);
      } catch (Exception e) {
        throw new IllegalArgumentException(String.format("A bean, %s, was mapped " +
            "which was not instantiable", type.getName()), e);
      }

      for (int i = 0; i < columnNumbers.size(); i++) {
        int columnNumber = columnNumbers.get(i);
        ColumnMapper<?> mapper = mappers.get(i);
        PropertyDescriptor property = properties.get(i);

        Object value = mapper.map(r, columnNumber, ctx);
        try {
          property.getWriteMethod().invoke(bean, value);
        } catch (IllegalAccessException e) {
          throw new IllegalArgumentException(String.format("Unable to access setter for " +
              "property, %s", property.getName()), e);
        } catch (InvocationTargetException e) {
          throw new IllegalArgumentException(String.format("Invocation target exception trying to " +
              "invoker setter for the %s property", property.getName()), e);
        } catch (NullPointerException e) {
          throw new IllegalArgumentException(String.format("No appropriate method to " +
              "write property %s", property.getName()), e);
        }
      }

      return bean;
    };
  }

  private Optional<PropertyDescriptor> descriptorForColumn(String columnName,
                                                           List<ColumnNameMatcher> columnNameMatchers) {
    for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
      String paramName = paramName(descriptor);
      for (ColumnNameMatcher strategy : columnNameMatchers) {
        if (strategy.columnNameMatches(columnName, paramName)) {
          return Optional.of(descriptor);
        }
      }
    }
    return Optional.empty();
  }

  private String paramName(PropertyDescriptor descriptor) {
    return Stream.of(descriptor.getReadMethod(), descriptor.getWriteMethod())
        .filter(Objects::nonNull)
        .map(method -> method.getAnnotation(ColumnName.class))
        .filter(Objects::nonNull)
        .map(ColumnName::value)
        .findFirst()
        .orElseGet(descriptor::getName);
  }
}
