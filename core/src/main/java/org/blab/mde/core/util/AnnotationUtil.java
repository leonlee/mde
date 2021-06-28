package org.blab.mde.core.util;

import com.google.common.base.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnnotationUtil {
  public static final String VALUE = "value";

  /**
   * Retrieve the <em>default value</em> of the {@code value} attribute of a single-element
   * Annotation, given an annotation instance.
   *
   * @param annotation the annotation instance from which to retrieve the default value
   * @return the default value, or {@code null} if not found
   * @see #getDefaultValue(Annotation, String)
   */
  public static Object getDefaultValue(Annotation annotation) {
    return getDefaultValue(annotation, VALUE);
  }

  /**
   * Retrieve the <em>default value</em> of a named attribute, given an annotation instance.
   *
   * @param annotation    the annotation instance from which to retrieve the default value
   * @param attributeName the name of the attribute value to retrieve
   * @return the default value of the named attribute, or {@code null} if not found
   * @see #getDefaultValue(Class, String)
   */
  public static Object getDefaultValue(Annotation annotation, String attributeName) {
    if (annotation == null) {
      return null;
    }
    return getDefaultValue(annotation.annotationType(), attributeName);
  }

  /**
   * Retrieve the <em>default value</em> of the {@code value} attribute of a single-element
   * Annotation, given the {@link Class annotation type}.
   *
   * @param annotationType the <em>annotation type</em> for which the default value should be
   *                       retrieved
   * @return the default value, or {@code null} if not found
   * @see #getDefaultValue(Class, String)
   */
  public static Object getDefaultValue(Class<? extends Annotation> annotationType) {
    return getDefaultValue(annotationType, VALUE);
  }

  /**
   * Retrieve the <em>default value</em> of a named attribute, given the {@link Class annotation
   * type}.
   *
   * @param annotationType the <em>annotation type</em> for which the default value should be
   *                       retrieved
   * @param attributeName  the name of the attribute value to retrieve.
   * @return the default value of the named attribute, or {@code null} if not found
   * @see #getDefaultValue(Annotation, String)
   */
  public static Object getDefaultValue(Class<? extends Annotation> annotationType, String attributeName) {
    if (annotationType == null || Strings.isNullOrEmpty(attributeName)) {
      return null;
    }
    try {
      return annotationType.getDeclaredMethod(attributeName).getDefaultValue();
    } catch (Throwable ex) {
      log.error("no attribute found", ex);
      return null;
    }
  }

  /**
   * Retrieve the <em>value</em> of the {@code value} attribute of a single-element Annotation,
   * given an annotation instance.
   *
   * @param annotation the annotation instance from which to retrieve the value
   * @return the attribute value, or {@code null} if not found unless the attribute value cannot be
   * retrieved due to an, in which case such an exception will be rethrown
   * @see #getValue(Annotation, String)
   */
  public static Object getValue(Annotation annotation) {
    return getValue(annotation, VALUE);
  }

  /**
   * Retrieve the <em>value</em> of a named attribute, given an annotation instance.
   *
   * @param annotation    the annotation instance from which to retrieve the value
   * @param attributeName the name of the attribute value to retrieve
   * @return the attribute value, or {@code null} if not found unless the attribute value cannot be
   * retrieved due to an, in which case such an exception will be rethrown
   * @see #getValue(Annotation)
   */
  public static Object getValue(Annotation annotation, String attributeName) {
    if (annotation == null || Strings.isNullOrEmpty(attributeName)) {
      return null;
    }
    try {
      Method method = annotation.annotationType().getDeclaredMethod(attributeName);
      makeAccessible(method);
      return method.invoke(annotation);
    } catch (InvocationTargetException ex) {
      throw new IllegalStateException(
          "Could not obtain value for annotation attribute '" + attributeName + "' in " + annotation, ex);
    } catch (Throwable ex) {
      return null;
    }
  }

  /**
   * Make the given field accessible, explicitly setting it accessible if necessary. The {@code
   * setAccessible(true)} method is only called when actually necessary, to avoid unnecessary
   * conflicts with a JVM SecurityManager (if active).
   *
   * @param field the field to make accessible
   * @see java.lang.reflect.Field#setAccessible
   */
  public static void makeAccessible(Field field) {
    if ((!Modifier.isPublic(field.getModifiers()) ||
        !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
        Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
      field.setAccessible(true);
    }
  }

  /**
   * Make the given method accessible, explicitly setting it accessible if necessary. The {@code
   * setAccessible(true)} method is only called when actually necessary, to avoid unnecessary
   * conflicts with a JVM SecurityManager (if active).
   *
   * @param method the method to make accessible
   * @see java.lang.reflect.Method#setAccessible
   */
  public static void makeAccessible(Method method) {
    if ((!Modifier.isPublic(method.getModifiers()) ||
        !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
      method.setAccessible(true);
    }
  }

  /**
   * Make the given constructor accessible, explicitly setting it accessible if necessary. The
   * {@code setAccessible(true)} method is only called when actually necessary, to avoid unnecessary
   * conflicts with a JVM SecurityManager (if active).
   *
   * @param ctor the constructor to make accessible
   * @see java.lang.reflect.Constructor#setAccessible
   */
  public static void makeAccessible(Constructor<?> ctor) {
    if ((!Modifier.isPublic(ctor.getModifiers()) ||
        !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
      ctor.setAccessible(true);
    }
  }

}
