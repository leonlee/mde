package org.blab.mde.fsm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface FsmTransitionHandler {
  String from() default "";

  String to() default "";

  /**
   * Indicate when handler will be invoked.
   *
   * @return true means  after transition, false means  before transition
   */
  boolean after() default false;
}
