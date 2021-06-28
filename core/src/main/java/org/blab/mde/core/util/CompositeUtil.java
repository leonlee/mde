package org.blab.mde.core.util;

import java.util.Arrays;

import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.meta.CompositeMeta;

import static org.blab.mde.core.CompositeEngineHolder.getEngine;


public class CompositeUtil {
  //"Dynamic Entity" suffix
  private static final String TYPE_SUFFIX = "cmpt";

  public static String nameOf(Class<?> clazz) {
    return String.format("%s$_$%s", clazz.getCanonicalName(), TYPE_SUFFIX);
  }

  public static CompositeMeta metaOf(Class<?> clazz) {
    return getEngine().metaOf(clazz);
  }

  public static CompositeMeta metaOf(String name) {
    return getEngine().metaOf(name);
  }

  public static <T> Class<T> typeOf(Class<T> clazz) {
    return getEngine().typeOf(clazz);
  }

  public static <T> Class<T> typeOf(String name) {
    return getEngine().typeOf(name);
  }

  public static <T> Class<T> sourceOf(String name) {
    return getEngine().sourceOf(name);
  }

  public static <C> C create(Class<?> type) {
    return getEngine().create(type);
  }

  public static <C> C createOf(Class<C> type) {
    return getEngine().createOf(type);
  }

  public static boolean isComposite(Class<?> type) {
    return type != null && Arrays.stream(type.getAnnotations())
        .anyMatch(it -> it.annotationType().getAnnotation(CompositeType.class) != null);
  }
}
