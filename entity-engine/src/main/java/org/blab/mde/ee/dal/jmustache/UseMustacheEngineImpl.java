package org.blab.mde.ee.dal.jmustache;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.sqlobject.config.Configurer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class UseMustacheEngineImpl implements Configurer {
  @Override
  public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
    registry.get(SqlStatements.class).setTemplateEngine(new MustacheEngine());
  }

  @Override
  public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
    configureForType(registry, annotation, sqlObjectType);
  }
}
