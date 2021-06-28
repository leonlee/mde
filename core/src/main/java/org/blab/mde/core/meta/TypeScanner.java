package org.blab.mde.core.meta;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


public class TypeScanner {
  private Reflections reflections;

  public TypeScanner(Set<String> packages) {
    ConfigurationBuilder builder = new ConfigurationBuilder()
        .filterInputsBy(new FilterBuilder().include(".*\\.class"))
        .forPackages(packages.toArray(new String[]{}));

    this.reflections = new Reflections(builder);
  }


  public Set<Class<? extends Annotation>> scanAnnotations(Class<? extends Annotation> annotationType) {
    @SuppressWarnings("unchecked")
    Set<Class<? extends Annotation>> annotations = reflections.getTypesAnnotatedWith(annotationType, true)
        .stream()
        .filter(Class::isAnnotation)
        .map(it -> (Class<? extends Annotation>) it)
        .collect(toSet());
    return annotations;
  }

  public Set<Class<?>> scanTypes(Set<Class<? extends Annotation>> annotationTypes) {
    Set<Class<?>> sourceTypes = new HashSet<>();
    for (Class<? extends Annotation> type : annotationTypes) {
      sourceTypes.addAll(reflections.getTypesAnnotatedWith(type, true));
    }
    return sourceTypes;
  }

  public <T extends Annotation> Set<T> scanTypes(Class<T> annotation) {
    return reflections.getTypesAnnotatedWith(annotation).stream()
        .map(it -> it.getAnnotation(annotation))
        .collect(toSet());
  }
}
