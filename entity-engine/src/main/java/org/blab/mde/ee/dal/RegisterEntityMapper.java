package org.blab.mde.ee.dal;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMappers;
import org.jdbi.v3.sqlobject.config.Configurer;
import org.jdbi.v3.sqlobject.config.ConfiguringAnnotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@ConfiguringAnnotation(RegisterEntityMapper.Impl.class)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RegisterEntityMapper {
  /**
   * The bean classes to map with EntityMapper.
   *
   * @return one or more bean classes.
   */
  Class<?>[] value();

  /**
   * Column name prefix for each bean type. If omitted, defaults to no prefix. If specified, must
   * have the same number of elements as {@link #value()}. Each <code>prefix</code> element is
   * applied to the <code>value</code> element at the same index.
   *
   * @return Column name prefixes corresponding pairwise to the elements in {@link #value()}.
   */
  String[] prefix() default {};

  class Impl implements Configurer {
    @Override
    public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
      RegisterEntityMapper registerEntityMapper = (RegisterEntityMapper) annotation;
      Class<?>[] beanClasses = registerEntityMapper.value();
      String[] prefixes = registerEntityMapper.prefix();
      RowMappers mappers = registry.get(RowMappers.class);
      if (prefixes.length == 0) {
        for (Class<?> beanClass : beanClasses) {
          mappers.register(EntityMapper.factory(beanClass));
        }
      } else if (prefixes.length == beanClasses.length) {
        for (int i = 0; i < beanClasses.length; i++) {
          mappers.register(EntityMapper.factory(beanClasses[i], prefixes[i]));
        }
      } else {
        throw new IllegalStateException("RegisterEntityMapper.prefix() must have the same number of elements as value()");
      }
    }

    @Override
    public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
      configureForType(registry, annotation, sqlObjectType);
    }
  }
}
