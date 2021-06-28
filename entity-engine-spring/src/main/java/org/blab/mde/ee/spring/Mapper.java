package org.blab.mde.ee.spring;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.blab.mde.core.exception.CrashedException;

import static org.blab.mde.core.util.CompositeUtil.createOf;
import static org.blab.mde.core.util.CompositeUtil.isComposite;
import static org.blab.mde.core.util.CompositeUtil.typeOf;
import static org.blab.mde.core.util.Guarder.requireTrue;


public class Mapper {
  private static final ExpressionParser elParser = new SpelExpressionParser();

  public static Map<String, Object> mapOf(Object source, Class<?> prototype) {
    if (isComposite(prototype)) {
      prototype = typeOf(prototype);
    }
    return mapTo(source, prototype, new HashMap<String, Object>());
  }

  public static <T> T mapTo(Object source, Class<T> targetType) {
    T target;
    try {
      target = isComposite(targetType) ? createOf(targetType) : targetType.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new CrashedException("mapping failed", e);
    }

    return mapTo(source, target);
  }

  public static <T> T mapTo(Object source, Object target) {
    return mapTo(source, target.getClass(), target);
  }

  @SuppressWarnings("unchecked")
  private static <T> T mapTo(Object source, Class<?> targetType, Object target) {
    try {
      BeanInfo sourceInfo = Introspector.getBeanInfo(source.getClass(), Introspector.USE_ALL_BEANINFO);
      Map<String, Method> properties = Arrays.stream(sourceInfo.getPropertyDescriptors())
          .map(it -> ImmutablePair.of(it.getName(), it.getReadMethod()))
          .filter(it -> it.left != null && it.right != null)
          .collect(Collectors.toMap(it -> it.left, it -> it.right));


      BeanInfo targetInfo = Introspector.getBeanInfo(targetType);
      for (PropertyDescriptor descriptor : targetInfo.getPropertyDescriptors()) {
        populateTarget(source, properties, targetType, descriptor, target);
      }
    } catch (IllegalAccessException | IntrospectionException
        | InvocationTargetException e) {
      throw new CrashedException("mapping failed", e);
    }

    return (T) target;
  }

  @SuppressWarnings("unchecked")
  private static <T> void populateTarget(Object source, Map<String, Method> properties,
                                         Class<?> targetType, PropertyDescriptor descriptor, T target)
      throws IllegalAccessException, InvocationTargetException {

    Method writeMethod = descriptor.getWriteMethod();
    if (writeMethod != null && Objects.equals(writeMethod.getDeclaringClass(), targetType)) {
      Binding binding = getBinding(descriptor, targetType);
      Object value;

      if (binding != null) {
        value = evaluateEL(source, binding);
      } else {
        value = readProperty(source, properties, descriptor.getName());
      }

      if (target instanceof Map) {
        ((Map<String, Object>) target).put(descriptor.getName(), value);
      } else {
        writeMethod.invoke(target, value);
      }
    }
  }

  private static Binding getBinding(PropertyDescriptor descriptor, Class<?> targetType) {
    Binding binding = descriptor.getReadMethod().getAnnotation(Binding.class);
    if (binding == null) {
      binding = descriptor.getWriteMethod().getAnnotation(Binding.class);
    }

    if (binding == null) {
      try {
        binding = targetType.getDeclaredField(descriptor.getName()).getAnnotation(Binding.class);
      } catch (NoSuchFieldException e) {
        throw new CrashedException("not found field {}", e, descriptor.getName());
      }
    }

    return binding;
  }

  private static Object readProperty(Object source, Map<String, Method> properties, String name)
      throws IllegalAccessException, InvocationTargetException {
    requireTrue(properties.containsKey(name), "not found property {}", name);

    return properties.get(name).invoke(source);
  }

  private static Object evaluateEL(Object source, Binding binding) {
    Expression exp = elParser.parseExpression(binding.value());
    EvaluationContext context = new StandardEvaluationContext(source);

    return exp.getValue(context);
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.FIELD})
  public @interface Binding {
    String value();
  }
}
