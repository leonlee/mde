package org.blab.mde.ee.dal;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.mapper.RowMapperFactory;

import java.lang.reflect.Type;
import java.util.Optional;

import org.blab.mde.ee.util.EntityUtil;

import static org.jdbi.v3.core.generic.GenericTypes.getErasedType;


public class EntityMapperFactory implements RowMapperFactory {
  @Override
  public Optional<RowMapper<?>> build(Type type, ConfigRegistry config) {
    Class<?> clazz = getErasedType(type);
    return EntityUtil.isEntity(clazz)
        ? Optional.of(new EntityMapper<>(clazz, ""))
        : Optional.empty();
  }
}
