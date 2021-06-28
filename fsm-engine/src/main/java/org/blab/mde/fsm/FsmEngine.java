package org.blab.mde.fsm;

import java.util.Map;

import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.fsm.annotation.FSM;
import org.blab.mde.fsm.core.FsmModel;
import org.blab.mde.fsm.core.FsmUtil;
import org.blab.mde.fsm.mixin.FsmMixin;

import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.core.util.Guarder.requireTrue;


public class FsmEngine implements CompositeEngineListener {
  @Override
  public void afterNewComposite(CompositeEngineContext context, Object instance) {
    if (instance.getClass().getDeclaredAnnotation(FSM.class) == null) {
      return;
    }

    requireTrue(instance instanceof FsmMixin, "FSM without FsmMixin {}", instance.getClass());

    Map<Class<?>, FsmModel> registry = context.getAttribute(FsmUtil.FSM_REGISTRY);
    requireNotNull(registry, "FSM Registry is null");
    //noinspection ConstantConditions
    ((FsmMixin) instance).setFsmModel(registry.get(instance.getClass()));
  }
}
