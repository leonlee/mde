package org.blab.mde.core.util;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.blab.mde.core.annotation.Arg;
import org.blab.mde.core.annotation.Delegate.DefaultType;
import org.blab.mde.core.annotation.Self;
import org.blab.mde.core.meta.DelegateMeta;
import lombok.extern.slf4j.Slf4j;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;


@Slf4j
public final class DelegateUtil {
  public static ElementMatcher<? super MethodDescription> matcherOf(DelegateMeta delegateMeta) {
    Junction<MethodDescription> junction = named(delegateMeta.getName())
        .and(takesArguments(delegateMeta.getParameterTypes()));

    if (!delegateMeta.getSource().equals(DefaultType.class)) {
      junction = junction.and(isDeclaredBy(delegateMeta.getSource()));
    }

    return junction;
  }

  public static ParameterBinder<?>[] parameterBinders() {
    return new ParameterBinder<?>[]{
        Self.Binder.INSTANCE,
        Arg.Binder.INSTANCE,
    };
  }

  public static long annotatedArgCount(Method method) {
    return Arrays.stream(method.getParameters())
        .filter(it -> it.getAnnotation(Arg.class) != null)
        .count();
  }

  public static int bindingParameterCount(Method method) {
    return Math.toIntExact(
        Arrays.stream(method.getParameterAnnotations())
            .filter(it -> Arrays.stream(it).anyMatch(DelegateUtil::isParameterBinder))
            .count());
  }

  private static boolean isParameterBinder(Annotation that) {
    return that instanceof Self;
  }

  public static String methodToString(Method method) {
    if (method == null) {
      return "";
    }

    return Arrays.stream(method.getParameters())
        .map(
            it ->
                String.format("%s(%s):%s ", it.getName(), it.getType(), Arrays.toString(it.getAnnotations())))
        .reduce("", (left, right) -> String.format("%s, %s", left, right));

  }
}

