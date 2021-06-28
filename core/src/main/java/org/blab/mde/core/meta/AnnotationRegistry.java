package org.blab.mde.core.meta;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.blab.mde.core.annotation.AnnotationType;
import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class AnnotationRegistry {
  private Map<AnnotationType, Set<Class<? extends Annotation>>> annotations;

  public AnnotationRegistry() {
    annotations = new HashMap<>();
    annotations.put(AnnotationType.COMPOSITE, new HashSet<>());
    annotations.put(AnnotationType.MIXIN, new HashSet<>());
    annotations.put(AnnotationType.EXTENSION, new HashSet<>());
    annotations.put(AnnotationType.PROPERTY, new HashSet<>());

    register(AnnotationType.COMPOSITE, Composite.class);
    register(AnnotationType.MIXIN, Mixin.class);
    register(AnnotationType.EXTENSION, Extension.class);
    register(AnnotationType.PROPERTY, Property.class);
  }

  public Set<Class<? extends Annotation>> annotationsOf(AnnotationType type) {
    return annotations.get(type);
  }

  public AnnotationRegistry register(AnnotationType targetType, Class<? extends Annotation> type) {
    annotations.get(targetType).add(type);
    log.debug("added annotations: {}", type);
    return this;
  }

  public AnnotationRegistry register(AnnotationType targetType, Collection<Class<? extends Annotation>> annotationTypes) {
    annotations.get(targetType).addAll(annotationTypes);
    log.debug("added annotations: {}", annotationTypes);
    return this;
  }

  public boolean contains(AnnotationType type, Annotation[] annotations) {
    if (annotations == null || annotations.length == 0) {
      return false;
    }

    log.debug("checking annotations {}", (Object[]) annotations);

    return Arrays.stream(annotations)
        .anyMatch(
            it ->
                this.annotations.get(type).contains(it.annotationType()));
  }
}
