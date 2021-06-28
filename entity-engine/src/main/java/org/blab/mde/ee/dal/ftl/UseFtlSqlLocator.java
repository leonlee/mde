package org.blab.mde.ee.dal.ftl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.blab.mde.ee.dal.ftl.internal.UseFreeMarkerTemplateSqlLocatorImpl;
import org.jdbi.v3.sqlobject.config.ConfiguringAnnotation;


@ConfiguringAnnotation(UseFreeMarkerTemplateSqlLocatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UseFtlSqlLocator {
}
