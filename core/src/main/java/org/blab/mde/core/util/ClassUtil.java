package org.blab.mde.core.util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.blab.mde.core.exception.CrashedException;


public interface ClassUtil {
  static Type getMethodType(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      //noinspection unchecked
      return clazz.getMethod(methodName, parameterTypes).getGenericReturnType();
    } catch (NoSuchMethodException e) {
      throw new CrashedException(e);
    }
  }

  static <T> T populate(Supplier<T> factory, Consumer<T> initializer) {
    T object = factory.get();
    initializer.accept(object);

    return object;
  }

  static ClassLoader getDefaultClassLoader() {
    ClassLoader classLoader = null;

    try {
      classLoader = Thread.currentThread().getContextClassLoader();
    } catch (Throwable ignored) {
    }

    if (classLoader == null) {
      classLoader = ClassUtils.class.getClassLoader();
      if (classLoader == null) {
        try {
          classLoader = ClassLoader.getSystemClassLoader();
        } catch (Throwable ignored) {
        }
      }
    }

    return classLoader;
  }
}
