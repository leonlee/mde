package org.blab.mde.fsm.mixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.util.Optional;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.fsm.core.FsmModel;
import org.blab.mde.fsm.core.MismatchedEventException;

import static org.blab.mde.core.util.Guarder.requireNotBlank;

@Mixin
public interface FsmMixin {
  Logger log = LoggerFactory.getLogger(FsmMixin.class);

  @Property
  FsmModel getFsmModel();

  void setFsmModel(FsmModel fsmModel);

  @Property
  String getState();

  void setState(String state);

  @Property
  String getLastEvent();

  void setLastEvent(String event);

  default String currentState() {
    return getState();
  }


  default void startFsm(String initState) {
    setState(initState);
  }

  default String lastEvent() {
    return getLastEvent();
  }

  default <R> R send(String event, Object... args) {
    requireNotBlank(event, "blank event");

    Optional<MethodHandle> handle = getFsmModel().findAction(getState(), event, args);
    if (!handle.isPresent()) {
      handleMismatchedEvent(event, args);
      return null;
    }

    setLastEvent(event);

    R result;
    result = invokeHandle(handle.get(), args);
    return result;
  }

  default void moveTo(String state) {
    if (currentState().equals(state)) {
      return;
    }

    String from = currentState();
    getFsmModel().findTransitionHandler(from, state, false)
        .forEach(it -> invokeHandle(it));

    setState(state);

    getFsmModel().findTransitionHandler(from, state, true)
        .forEach(it -> invokeHandle(it));
  }

  @SuppressWarnings("unchecked")
  default <R> R invokeHandle(MethodHandle handle, Object... args) {
    R result = null;
    try {
      if (args.length == 0) {
        result = (R) handle.bindTo(this).invoke();
      } else {
        result = (R) handle.bindTo(this).invokeWithArguments(args);
      }
    } catch (Throwable e) {
      handleException(e, args);
    }
    return result;
  }

  /**
   * Handler of exception when invoking method handle.
   *
   * @param e    throwable
   * @param args context args
   */
  default void handleException(Throwable e, Object[] args) {
    log.error("invoke handler failed, state: {} event: {} args: {}",
        currentState(), lastEvent(), args, e);

    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else {
      throw new CrashedException("invoke handler failed", e);
    }
  }

  default void handleMismatchedEvent(String event, Object[] args) {
    throw new MismatchedEventException("mismatched event: {} in {} with {}", event, currentState(), args);
  }
}
