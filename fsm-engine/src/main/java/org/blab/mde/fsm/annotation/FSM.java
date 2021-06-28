package org.blab.mde.fsm.annotation;

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
public @interface FSM {
  /**
   * Name of composite for CDI.
   *
   * @return name of composite
   */
  String value() default "";

  String name() default "";

  String scope() default CompositeType.SCOPE_PROTOTYPE;

  Class<?> states();

  Class<?> events();
}
