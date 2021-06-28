package org.blab.mde.view.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.blab.mde.core.annotation.CompositeType;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CompositeType
public @interface View {
  String value() default "";

  String name() default "";

  String scope() default CompositeType.SCOPE_SINGLETON;
}
