package org.blab.mde.core.meta;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Delegate.BindingMode;
import lombok.Data;

import static org.blab.mde.core.util.DelegateUtil.annotatedArgCount;
import static org.blab.mde.core.util.DelegateUtil.bindingParameterCount;
import static org.blab.mde.core.util.DelegateUtil.methodToString;
import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.core.util.Guarder.requireTrue;


@Data
public class DelegateMeta implements Comparable<DelegateMeta> {
  private Class<?> source;
  private String name;
  private Class<?>[] parameterTypes;

  private BindingMode mode;
  private short priority;
  private Class<?>[] exclusions;

  private Method target;

  public DelegateMeta(Method target) {
    validate(target);

    this.target = target;

    Delegate annotation = target.getAnnotation(Delegate.class);
    requireNotNull(annotation, "invalid delegate");

    this.source = annotation.source();
    this.name = annotation.name();
    this.parameterTypes = annotation.parameterTypes();

    this.mode = annotation.mode();
    this.priority = annotation.priority();
    this.exclusions = annotation.exclusions();
  }

  private void validate(Method target) {
    requireTrue(Modifier.isStatic(target.getModifiers()), "invalid delegate target: it should be static");

    int bindingParameterCount = bindingParameterCount(target);
    long annotatedArgCount = annotatedArgCount(target);
    int parameterCount = target.getParameterCount();

    requireTrue(bindingParameterCount == 0 || parameterCount == bindingParameterCount + annotatedArgCount,
        "invalid delegate: maybe lack Arg annotations {}", methodToString(target));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DelegateMeta that = (DelegateMeta) o;

    return new EqualsBuilder()
        .append(name, that.name)
        .append(parameterTypes, that.parameterTypes)
        .build();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(name).append(parameterTypes).build();
  }

  @Override
  public int compareTo(DelegateMeta that) {
    return new CompareToBuilder().append(that.priority, priority)
        .append(target.getDeclaringClass().getCanonicalName(),
            that.target.getDeclaringClass().getCanonicalName()).build();
  }
}
