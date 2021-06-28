package org.blab.mde.ee.dal.jmustache;

import org.jdbi.v3.sqlobject.config.ConfiguringAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@ConfiguringAnnotation(UseMustacheEngineImpl.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UseMustacheEngine {
}
