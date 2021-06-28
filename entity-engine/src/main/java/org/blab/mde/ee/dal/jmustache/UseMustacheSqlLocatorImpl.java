package org.blab.mde.ee.dal.jmustache;

import com.samskivert.mustache.Template;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.jdbi.v3.sqlobject.SqlObjects;
import org.jdbi.v3.sqlobject.config.Configurer;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireNotNull;


@Slf4j
public class UseMustacheSqlLocatorImpl implements Configurer {
  private static MustacheSqlLocator sqlLocator = new MustacheSqlLocator();

  @Override
  public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
    SqlLocator locator = (type, method, config) -> {
      String templateName = SqlAnnotations.getAnnotationValue(method, sql -> sql).orElseGet(method::getName);
      Template template = sqlLocator.findTemplate(sqlObjectType, templateName);
      requireNotNull(template, "mustache template {} is null", templateName);

      return templateName;
    };

    TemplateEngine templateEngine = (templateName, ctx) -> {
      Template template = sqlLocator.findTemplate(sqlObjectType, templateName);

      StringWriter writer = new StringWriter();
      Map<String, Object> context = new HashMap<>(ctx.getAttributes().size() + 1);
      context.putAll(ctx.getAttributes());
      context.put(templateName, true);

      template.execute(context, writer);

      return writer.toString();
    };
    registry.get(SqlObjects.class).setSqlLocator(locator);
    registry.get(SqlStatements.class).setTemplateEngine(templateEngine);
  }

  @Override
  public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
    configureForType(registry, annotation, sqlObjectType);
  }
}
