package org.blab.mde.ee.dal;

import org.jdbi.v3.core.mapper.JoinRow;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.blab.mde.ee.EntityEngine;
import org.blab.mde.ee.EntityEngineHolder;

public class EntityJoinRowMapper implements RowMapper<EntityJoinRow> {

  private final Type[] types;

  private EntityJoinRowMapper(Type[] types) {
    EntityEngine engine = EntityEngineHolder.getEngine();
    this.types = Arrays.stream(types)
        .map(type -> engine.typeOf(type.getClass()))
        .toArray(Type[]::new);
  }

  @Override
  public EntityJoinRow map(ResultSet r, StatementContext ctx)
      throws SQLException {
    final Map<Type, Object> entries = new HashMap<>(types.length);
    for (Type type : types) {
      entries.put(type, ctx.findRowMapperFor(type)
          .orElseThrow(() -> new IllegalArgumentException(
              "No row mapper registered for " + type))
          .map(r, ctx));
    }
    return new EntityJoinRow(entries);
  }

  @Override
  public RowMapper<EntityJoinRow> specialize(ResultSet r, StatementContext ctx) throws SQLException {
    RowMapper<?>[] mappers = new RowMapper[types.length];
    for (int i = 0; i < types.length; i++) {
      Type type = types[i];
      mappers[i] = ctx.findRowMapperFor(type)
          .orElseThrow(() -> new IllegalArgumentException("No row mapper registered for " + type))
          .specialize(r, ctx);
    }

    return (rs, context) -> {
      final Map<Type, Object> entries = new HashMap<>(types.length);
      for (int i = 0; i < types.length; i++) {
        Type type = types[i];
        RowMapper<?> mapper = mappers[i];
        entries.put(type, mapper.map(r, ctx));
      }
      return new EntityJoinRow(entries);
    };
  }

  /**
   * Create a JoinRowMapper that maps each of the given types and returns a {@link JoinRow} with the
   * resulting values.
   *
   * @param classes the types to extract
   * @return a JoinRowMapper that extracts the given types
   */
  public static EntityJoinRowMapper forTypes(Type... classes) {
    return new EntityJoinRowMapper(classes);
  }
}
