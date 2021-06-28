package org.blab.mde.core.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

import org.blab.mde.core.exception.CrashedException;


public interface CompositeDecorator {
  Logger log = LoggerFactory.getLogger(CompositeDecorator.class);

  static CompositeDecorator of(Class<CompositeDecorator> clazz) {
    try {
      if (clazz == null) {
        return new DefaultDecorator();
      } else {
        return clazz.newInstance();
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new CrashedException("can't create CompositeDecorator instance", e);
    }
  }

  static Annotation[] annotationsOf(Class<CompositeDecorator> clazz) {
    Annotation[] annotations = of(clazz).annotatedWith();

    log.debug("decorated by annotations: {}", (Object[]) annotations);

    return annotations == null ? new Annotation[0] : annotations;
  }

  default Annotation[] annotatedWith() {
    return new Annotation[0];
  }

  class DefaultDecorator implements CompositeDecorator {
  }
}
