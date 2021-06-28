package org.blab.mde.fsm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.meta.BaseComposite;
import org.blab.mde.core.meta.CompositeMeta;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.fsm.annotation.FSM;
import org.blab.mde.fsm.annotation.FsmAction;
import org.blab.mde.fsm.annotation.FsmTransitionHandler;
import org.blab.mde.fsm.mixin.FsmMixin;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireTrue;


@Slf4j
public class FsmBuilderVisitor implements MetaElementBuilderVisitor {

  @Override
  public void visit(CompositeEngineContext context, CompositeMeta element) {
    Class<? extends BaseComposite> type = element.getType();
    FSM smAnnotation = type.getDeclaredAnnotation(FSM.class);
    if (smAnnotation == null) {
      return;
    }
    requireTrue(FsmMixin.class.isAssignableFrom(type), "FSM without FsmMixin {}", type);

    String[] states = extractStrings(smAnnotation.states());
    String[] events = extractStrings(smAnnotation.events());

    FsmModel fsmModel = createFsmModel(type, states, events);
    registerHandles(context, type, fsmModel);
  }

  private FsmModel createFsmModel(Class<? extends BaseComposite> type, String[] states, String[] events) {
    FsmModel fsmModel = new FsmModel(states, events);

    Method[] methods = type.getMethods();
    Arrays.stream(methods)
        .filter(it -> it.getAnnotationsByType(FsmAction.class).length > 0)
        .forEach(fsmModel::addAction);

    Arrays.stream(methods)
        .filter(it -> it.getAnnotationsByType(FsmTransitionHandler.class).length > 0)
        .forEach(fsmModel::addTransitionHandler);

    return fsmModel;
  }

  private String[] extractStrings(Class states) {
    return Arrays.stream(states.getFields())
        .map(this::extractString).toArray(String[]::new);
  }

  private String extractString(Field field) {
    String value;
    try {
      value = (String) field.get(null);
      requireNotBlank(value);
    } catch (IllegalAccessException e) {
      throw new CrashedException("bad string constant {}", e, field.getName());
    }
    return value;
  }


  private synchronized void registerHandles(CompositeEngineContext context, Class<? extends BaseComposite> type, FsmModel model) {
    Map<Class<? extends BaseComposite>, FsmModel> map;
    if (!context.hasAttribute(FsmUtil.FSM_REGISTRY)) {
      map = new HashMap<>();
      context.setAttribute(FsmUtil.FSM_REGISTRY, map);
    } else {
      map = context.getAttribute(FsmUtil.FSM_REGISTRY);
    }

    map.put(type, model);
  }
}
