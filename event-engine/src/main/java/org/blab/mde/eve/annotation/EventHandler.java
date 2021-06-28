package org.blab.mde.eve.annotation;

import com.google.common.eventbus.Subscribe;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EventHandler {
  class Type implements EventHandler {
    @Override
    public Class<? extends Annotation> annotationType() {
      return EventHandler.class;
    }
  }

  class SubscriberType implements Subscribe {
    @Override
    public Class<? extends Annotation> annotationType() {
      return Subscribe.class;
    }
  }
}
