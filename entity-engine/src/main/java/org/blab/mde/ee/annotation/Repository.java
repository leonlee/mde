package org.blab.mde.ee.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.blab.mde.core.annotation.CompositeType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@CompositeType
public @interface Repository {
  String value() default "";

  String name() default "";

  String scope() default CompositeType.SCOPE_SINGLETON;
}
