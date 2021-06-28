package org.blab.mde.fsm.core;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.blab.mde.fsm.annotation.FsmAction;
import org.blab.mde.fsm.annotation.FsmTransitionHandler;
import lombok.Data;

import static org.blab.mde.core.util.Guarder.requireFalse;
import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.fsm.core.FsmUtil.blankToGlobal;
import static org.blab.mde.fsm.core.FsmUtil.generateActionId;
import static org.blab.mde.fsm.core.FsmUtil.generateHandlerId;
import static org.blab.mde.fsm.core.FsmUtil.makeMethodHandle;


@Data
public class FsmModel {
  private String[] states;
  private String[] events;
  private Map<String, Action> actions;
  private Map<String, TransitionHandler> transitionHandlers;

  public FsmModel(String[] states, String[] events) {
    this.states = states;
    this.events = events;
    this.actions = new HashMap<>();
    this.actions = new HashMap<>(events.length);
    this.transitionHandlers = new HashMap<>();
  }

  public void addAction(Method method) {
    Action action = new Action(method);
    requireFalse(actions.containsKey(action.getId()),
        "duplicated FsmAction {}", action.getId());

    actions.put(action.getId(), action);
  }

  public void addTransitionHandler(Method method) {
    TransitionHandler handler = new TransitionHandler(method);
    requireFalse(transitionHandlers.containsKey(handler.getId()),
        "duplicated TransitionHandler {}", handler.getId());

    transitionHandlers.put(handler.getId(), handler);
  }

  public Optional<MethodHandle> findAction(String state, String event, Object[] args) {
    Class<?>[] argTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    Action action = determineAction(state, event, argTypes)
        .orElseGet(() -> determineAction(blankToGlobal(null), event, argTypes)
            .orElse(null));

    return action == null ? Optional.empty() : Optional.of(action.getHandle());
  }

  private Optional<Action> determineAction(String state, String event, Class<?>[] argTypes) {
    String key = generateActionId(state, event, argTypes);
    Action action = actions.get(key);
    return Optional.ofNullable(action);
  }

  public List<MethodHandle> findTransitionHandler(String from, String to, boolean post) {
    List<MethodHandle> handles = new ArrayList<>();

    determineHandler(blankToGlobal(null), blankToGlobal(null), post).ifPresent(it -> handles.add(it.getHandle()));
    determineHandler(blankToGlobal(null), to, post).ifPresent(it -> handles.add(it.getHandle()));
    determineHandler(from, blankToGlobal(null), post).ifPresent(it -> handles.add(it.getHandle()));
    determineHandler(from, to, post).ifPresent(it -> handles.add(it.getHandle()));

    return handles;
  }

  private Optional<TransitionHandler> determineHandler(String from, String to, boolean post) {
    String key = generateHandlerId(from, to, post);
    TransitionHandler handler = transitionHandlers.get(key);
    return Optional.ofNullable(handler);
  }

  @Data
  public static class Action {
    private String id;
    private String state;
    private String event;
    private Method method;
    private MethodHandle handle;

    public Action(Method method) {
      FsmAction annotation = method.getAnnotation(FsmAction.class);
      requireNotNull(annotation, "invalid FsmAction {}", method.getName());

      this.state = blankToGlobal(annotation.state());
      this.event = annotation.event();
      requireNotBlank(event, "invalid FsmAction {}", annotation);

      this.method = method;
      this.handle = makeMethodHandle(method);
      this.id = generateActionId(state, event, method.getParameterTypes());
    }
  }

  @Data
  public static class TransitionHandler {
    private String id;
    private String from;
    private String to;
    private boolean post;
    private Method method;
    private MethodHandle handle;

    public TransitionHandler(Method method) {
      FsmTransitionHandler annotation = method.getAnnotation(FsmTransitionHandler.class);
      requireNotNull(annotation, "invalid FSM TransitionHandler {}", method.getName());

      this.from = blankToGlobal(annotation.from());
      this.to = blankToGlobal(annotation.to());
      this.post = annotation.after();
      this.method = method;
      this.handle = makeMethodHandle(method);
      this.id = generateHandlerId(from, to, post);
    }
  }
}
