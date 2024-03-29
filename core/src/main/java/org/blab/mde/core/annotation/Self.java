package org.blab.mde.core.annotation;

import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parameters that are annotated with this annotation will be assigned a reference to the
 * instrumented object, if the instrumented method is not static. Otherwise, the method with this
 * parameter annotation will be excluded from the list of possible binding candidates of the static
 * source method.
 *
 * @see net.bytebuddy.implementation.MethodDelegation
 * @see net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Self {

  /**
   * Determines if the annotated parameter should be bound to {@code null} when intercepting a
   * {@code static} method.
   *
   * @return {@code true} if the annotated parameter should be bound to {@code null} as a fallback.
   */
  boolean optional() default false;

  /**
   * A binder for handling the
   * {@link net.bytebuddy.implementation.bind.annotation.This}
   * annotation.
   *
   * @see TargetMethodAnnotationDrivenBinder
   */
  enum Binder implements TargetMethodAnnotationDrivenBinder.ParameterBinder<Self> {

    /**
     * The singleton instance.
     */
    INSTANCE;

    @Override
    public Class<Self> getHandledType() {
      return Self.class;
    }

    @Override
    public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<Self> annotation,
                                                           MethodDescription source,
                                                           ParameterDescription target,
                                                           Implementation.Target implementationTarget,
                                                           Assigner assigner,
                                                           Assigner.Typing typing) {
      if (target.getType().isPrimitive()) {
        throw new IllegalStateException(target + " uses a primitive type with a @Self annotation");
      } else if (target.getType().isArray()) {
        throw new IllegalStateException(target + " uses an array type with a @Self annotation");
      } else if (source.isStatic() && !annotation.loadSilent().optional()) {
        return MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
      }
      return new MethodDelegationBinder.ParameterBinding.Anonymous(source.isStatic()
          ? NullConstant.INSTANCE
          : new StackManipulation.Compound(MethodVariableAccess.loadThis(),
          assigner.assign(implementationTarget.getInstrumentedType().asGenericType(), target.getType(), typing)));
    }
  }
}


