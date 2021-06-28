package org.blab.mde.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CompositeType
public @interface Composite {

  /**
   * Name of composite for CDI.
   *
   * @return name of composite
   */
  String value() default "";

  String name() default "";

  /**
   * Scope of composite, only supports singleton and prototype.
   *
   * @return scope
   */
  String scope() default CompositeType.SCOPE_PROTOTYPE;

  /**
   * Version for <code>Serializable</code> classes.
   *
   * @return version in long, -1L means not implements <code>Serializable</code>
   */
  long version() default -1L;

  Class<? extends CompositeDecorator> decorator() default CompositeDecorator.DefaultDecorator.class;
}
