package org.blab.mde.ee.dal.ftl.internal;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.jdbi.v3.sqlobject.SqlObjects;
import org.jdbi.v3.sqlobject.config.Configurer;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

import org.blab.mde.core.util.Guarder;
import org.blab.mde.ee.dal.ftl.FT;
import org.blab.mde.ee.dal.ftl.FTFile;
import org.blab.mde.ee.dal.ftl.FreeMarkerTemplateSqlLocator;
import org.blab.mde.ee.dal.ftl.statement.FreeMarkerSqlParser;


public class UseFreeMarkerTemplateSqlLocatorImpl implements Configurer {

  @Override
  public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
    SqlLocator sqlLocator = (type, method, config) -> {
      Optional<String> optional = SqlAnnotations.getAnnotationValue(method, sql -> sql);
      String templateName = optional.orElseGet(method::getName);
      FTFile ftFile = FreeMarkerTemplateSqlLocator.findFTFile(type);

      Guarder.requireNotNull(ftFile, "类型{}对应的SQL模板文件不存在{}", type);

      Guarder.requireTrue(ftFile.isDefined(templateName), "{}模板不存在，在类型{}中", templateName, type);
      return templateName;
    };

    TemplateEngine templateEngine = (templateName, ctx) -> {
      FTFile ftFile = FreeMarkerTemplateSqlLocator.findFTFile(sqlObjectType);

      FT ft = ftFile.find(templateName);

      return ft.render().sqlFT();
    };


    registry.get(SqlObjects.class).setSqlLocator(sqlLocator);
    registry.get(SqlStatements.class)
        .setTemplateEngine(templateEngine)
        .setSqlParser(new FreeMarkerSqlParser());

  }

  @Override
  public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType,
                                 Method method) {
    configureForType(registry, annotation, sqlObjectType);
  }
}
