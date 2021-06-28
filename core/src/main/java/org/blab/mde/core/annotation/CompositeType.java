package org.blab.mde.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CompositeType indicates the annotated annotation is a type of composite classifier.<br/> All
 * composite annotations should have:<br/>
 * <pre><code>
 *   String value() default ""; //name of composite type that supports create composite by name
 *   String scope() default CompositeType.SCOPE_SINGLETON;
 * </code></pre>
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CompositeType {
  String SCOPE_SINGLETON = "singleton";
  String SCOPE_PROTOTYPE = "prototype";

  String ATTR_VALUE = "value";
  String ATTR_NAME = "name";
  String ATTR_SCOPE = "scope";
  String ATTR_DECORATOR = "decorator";
}
