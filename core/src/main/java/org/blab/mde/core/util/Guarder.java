package org.blab.mde.core.util;

import java.util.Collection;
import java.util.function.BooleanSupplier;

import org.blab.mde.core.exception.CrashedException;


public class Guarder {

  private static final String PREDICATE_FAILED = "predicate failed";

  public static boolean requireTrue(boolean predicate) {
    return requireTrue(predicate, "predicate 'true' failed ");
  }

  public static boolean requireTrue(boolean predicate, String message, Object... args) {
    return predicate(() -> predicate, message, args);
  }

  public static boolean requireTrue(boolean predicate, CrashedException exception) {
    return predicate(() -> predicate, exception);
  }


  public static boolean requireTrue(boolean predicate, Class<? extends CrashedException> exClass) {
    return predicate(() -> predicate, exClass);
  }

  public static boolean requireFalse(boolean predicate) {
    return requireTrue(!predicate);
  }

  public static boolean requireFalse(boolean predicate, String message, Object... args) {
    return requireTrue(!predicate, message, args);
  }

  public static boolean requireFalse(boolean predicate, CrashedException exception) {
    return requireTrue(!predicate, exception);
  }

  public static boolean requireFalse(boolean predicate, Class<? extends CrashedException> exClass) {
    return requireTrue(!predicate, exClass);
  }

  public static boolean requireNull(Object object) {
    return requireNull(object, "predicate 'not null' failed");
  }

  public static boolean requireNull(Object object, String message, Object... args) {
    return predicate(() -> object == null, message, args);
  }

  public static boolean requireNotNull(Object object, String message, Object... args) {
    return predicate(() -> object != null, message, args);
  }

  public static boolean predicate(BooleanSupplier predicate) {
    return predicate(predicate, PREDICATE_FAILED);
  }

  private static boolean predicate(BooleanSupplier predicate, String message, Object... args) {
    if (predicate.getAsBoolean()) {
      return true;
    }
    throw new CrashedException(message, args);
  }

  public static boolean predicate(BooleanSupplier predicate, CrashedException exception) {
    if (predicate.getAsBoolean()) {
      return true;
    }
    throw exception;
  }

  public static boolean predicate(BooleanSupplier predicate, Class<? extends CrashedException> exClass) {
    if (predicate.getAsBoolean()) {
      return true;
    }
    try {
      throw exClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new CrashedException(PREDICATE_FAILED);
    }
  }

  public static boolean requireNotNull(Object object) {
    return requireNotNull(object, "predicate 'not null' failed");
  }

  public static boolean requireEmpty(Collection collection) {
    return requireEmpty(collection, "predicate 'empty' failed");
  }

  public static boolean requireEmpty(Collection collection, String message, Object... args) {
    return predicate(() -> collection == null || collection.size() == 0, message, (Object[]) args);
  }

  public static boolean requireNotEmpty(Collection collection) {
    return requireNotEmpty(collection, "predicate 'not empty' failed");
  }

  public static boolean requireNotEmpty(Collection collection, String message, Object... args) {
    return predicate(() -> collection != null && collection.size() > 0, message, (Object[]) args);
  }


  public static boolean requireEmpty(Object[] objects) {
    return requireEmpty(objects, "predicate 'empty' failed");
  }

  public static boolean requireEmpty(Object[] objects, String message, Object... args) {
    return predicate(() -> objects == null || objects.length == 0, message, (Object[]) args);
  }

  public static boolean requireNotEmpty(Object[] objects) {
    return requireNotEmpty(objects, "predicate 'not empty' failed");
  }

  public static boolean requireNotEmpty(Object[] objects, String message, Object... args) {
    return predicate(() -> objects != null && objects.length > 0, message, (Object[]) args);
  }

  public static boolean requireBlank(String value, String message, Object... args) {
    return predicate(() -> value == null || value.length() == 0, message, (Object[]) args);
  }

  public static boolean requireBlank(String value) {
    return requireBlank(value, "predicate 'blank' failed");
  }

  public static boolean requireNotBlank(String value, String message, Object... args) {
    return predicate(() -> value != null && value.length() > 0, message, (Object[]) args);
  }

  public static boolean requireNotBlank(String value) {
    return requireNotBlank(value, "predicate 'not blank' failed");
  }

  public static <T> T unsupportedOperation() {
    requireTrue(false, "unsupported operation");
    return null;
  }
}
