package org.blab.mde.ee.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.JDBCType;

import org.blab.mde.core.annotation.PropertyType;

/**
 * Declare property definition, only supports place on getter method that only has return type and
 * no parameters.<br/>
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@PropertyType
public @interface Field {
  String name() default "";

  String value() default "";

  JDBCType type() default JDBCType.VARCHAR;

  boolean primaryKey() default false;

  boolean indexed() default false;

  boolean unique() default false;
}
