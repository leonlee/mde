package org.blab.mde.core.util;

import net.bytebuddy.description.method.MethodDescription;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

import org.blab.mde.core.annotation.PropertyType;
import org.blab.mde.core.exception.CrashedException;

import static org.blab.mde.core.util.Accessor.GETTER;
import static org.blab.mde.core.util.Accessor.ISER;
import static org.blab.mde.core.util.Accessor.SETTER;
import static org.blab.mde.core.util.Guarder.requireFalse;
import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.core.util.Guarder.requireTrue;


public class MixinUtil {
  public static boolean isProperty(Method method) {
    return Arrays
        .stream(method.getAnnotations())
        .anyMatch(
            it ->
                it.annotationType().getAnnotation(PropertyType.class) != null);
  }

  public static boolean isBehavior(Method method) {
    return !isProperty(method)
        && !method.getDeclaringClass().equals(Object.class)
        && method.isDefault();
  }

  public static boolean isAccessor(Method method) {
    return accessorOf(method).isPresent();
  }

  public static Optional<Accessor> accessorOf(Method method) {
    requireNotNull(method);

    String name = method.getName();
    int parameterCount = method.getParameterCount();
    Class<?> returnType = method.getReturnType();

    Accessor accessor = null;
    if (name.startsWith(Accessor.GETTER.prefix) && parameterCount == 0 && returnType != Void.TYPE) {
      accessor = Accessor.GETTER;
    } else if (name.startsWith(Accessor.ISER.prefix) && parameterCount == 0 && returnType != Void.TYPE) {
      accessor = Accessor.ISER;
    } else if (name.startsWith(SETTER.prefix) && parameterCount == 1 && returnType == Void.TYPE) {
      accessor = SETTER;
    }

    return Optional.ofNullable(accessor);
  }

  public static String propertyOf(Method method) {
    String name = method.getName();
    requireTrue(isAccessor(method), "illegal property name {}", name);
    Optional<Accessor> accessor = accessorOf(method);

    requireTrue(accessor.isPresent(), "not a accessor {]", method.getName());
    validateProperty(method, accessor.get());

    return accessor
        .map(it -> StringUtil.decapitalize(name.substring(it.length)))
        .orElseThrow(CrashedException::new);
  }

  private static void validateProperty(Method method, Accessor accessor) {
    String pairName;
    Method pairMethod = null;
    String name = method.getName();
    Class<?> declaringClass = method.getDeclaringClass();

    try {
      switch (accessor) {
        case GETTER:
          pairName = SETTER.prefix + name.substring(GETTER.length);
          pairMethod = declaringClass.getMethod(pairName, method.getReturnType());
          break;
        case ISER:
          pairName = SETTER.prefix + name.substring(ISER.length);
          pairMethod = declaringClass.getMethod(pairName, method.getReturnType());
          break;
        case SETTER:
          pairName = GETTER.prefix + name.substring(SETTER.length);
          pairMethod = declaringClass.getMethod(pairName, Void.TYPE);
          if (pairMethod == null) {
            pairName = ISER.prefix + name.substring(SETTER.length);
            pairMethod = declaringClass.getMethod(pairName, Void.TYPE);
          }
          break;
      }
    } catch (NoSuchMethodException e) {
      throw new CrashedException(e);
    }

    requireFalse(pairMethod == null, "lack accessor of property {}", method);
  }

  public static Type typeOfProperty(Method method) {
    requireNotNull(method);
    Type type = null;
    Optional<Accessor> accessor = accessorOf(method);

    if (accessor.isPresent()) {
      switch (accessor.get()) {
        case SETTER:
          type = method.getGenericParameterTypes()[0];
          break;
        case ISER:
        case GETTER:
          type = method.getGenericReturnType();
      }
    } else {
      throw new CrashedException("invalid property {}", method.getName());
    }
    return type;
  }

  public static boolean isAccessor(String name) {
    requireNotNull(name);
    return Accessor.isPrefix(name);
  }

  public static boolean isProperty(MethodDescription description) {
    boolean anyMatch = description.getDeclaredAnnotations()
        .stream()
        .anyMatch(
            it ->
                it.getAnnotationType().getDeclaredAnnotations()
                    .stream()
                    .anyMatch(
                        ad ->
                            ad.getAnnotationType().getCanonicalName().equals(PropertyType.class.getCanonicalName())));
    return anyMatch;
  }
}

enum Accessor {
  GETTER("get", 3),
  SETTER("set", 3),
  ISER("is", 2);

  String prefix;
  int length;

  Accessor(String prefix, int length) {
    this.prefix = prefix;
    this.length = length;
  }

  public static boolean isPrefix(String prefix) {
    return prefix.startsWith(GETTER.prefix)
        || prefix.startsWith(SETTER.prefix)
        || prefix.startsWith(ISER.prefix);
  }
}
