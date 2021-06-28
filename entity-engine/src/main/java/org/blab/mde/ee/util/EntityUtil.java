package org.blab.mde.ee.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.blab.mde.core.mixin.Composable;
import org.blab.mde.ee.annotation.Aggregate;
import org.blab.mde.ee.annotation.Entity;

import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.ee.EntityEngineHolder.getEngine;


public interface EntityUtil {
  static boolean isEntity(Class<?> type) {
    return type.isAnnotationPresent(Entity.class)
        || type.isAnnotationPresent(Aggregate.class);
  }

  static <T> T singleton(Class<T> clazz) {
    requireNotNull(clazz, "please provide entityName or entityClass");
    //noinspection unchecked
    return (T) SingletonContext.INSTANCE.make(clazz);
  }

  static <E> E create(Class<?> clazz) {
    return getEngine().newEntity(clazz);
  }

  static <E> E createOf(Class<E> clazz) {
    return getEngine().newEntity(clazz);
  }

  enum SingletonContext {
    INSTANCE;

    ConcurrentMap<Class<?>, Composable> mapping;

    SingletonContext() {
      mapping = new ConcurrentHashMap<>();
    }

    public <T extends Composable> T make(Class<?> clazz) {
      if (!mapping.containsKey(clazz)) {
        mapping.putIfAbsent(clazz, getEngine().newEntity(clazz));
      }

      //noinspection unchecked
      return (T) mapping.get(clazz);
    }
  }
}
