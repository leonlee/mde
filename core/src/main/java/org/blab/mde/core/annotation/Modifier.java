package org.blab.mde.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.blab.mde.core.annotation.Modifier.Modifiers;


@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(Modifiers.class)
public @interface Modifier {
  int VOLATILE = 1;
  int FINAL = 2;
  int TRANSIENT = 4;

  int value() default 0;

  @Target(value = {ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  @interface Modifiers {
    Modifier[] value() default {};
  }
}
