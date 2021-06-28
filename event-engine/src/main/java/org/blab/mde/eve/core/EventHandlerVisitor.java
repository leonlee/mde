package org.blab.mde.eve.core;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.SuperMethodCall;

import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.meta.BaseComposite;
import org.blab.mde.core.meta.BehaviorMeta;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.eve.annotation.EventHandler;

import static net.bytebuddy.matcher.ElementMatchers.is;


public class EventHandlerVisitor implements MetaElementBuilderVisitor {
  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, BehaviorMeta element) {
    boolean isEventHandler = element.getAnnotations()
        .stream()
        .anyMatch(annotation ->
            EventHandler.class.isAssignableFrom(annotation.annotationType()));

    if (!isEventHandler) {
      return builder;
    }

    builder = builder.method(is(element.getSource()))
        .intercept(SuperMethodCall.INSTANCE)
        .annotateMethod(new EventHandler.SubscriberType());

    return builder;
  }
}
