package org.blab.mde.ee.dal;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMappers;
import org.jdbi.v3.sqlobject.config.Configurer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RegisterEntityJoinRowMapperImpl implements Configurer {
  @Override
  public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
    configureForType(registry, annotation, sqlObjectType);
  }

  @Override
  public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
    RegisterEntityJoinRowMapper registerJoinRowMapper = (RegisterEntityJoinRowMapper) annotation;
    registry.get(RowMappers.class).register(EntityJoinRowMapper.forTypes(registerJoinRowMapper.value()));
  }
}
