package org.blab.mde.fsm.core;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Primitives;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import org.blab.mde.core.exception.CrashedException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireNotNull;


@UtilityClass
@Slf4j
public class FsmUtil {
  public static final String FSM_REGISTRY = "fsm_mh_reg";
  public static final String STATE_GLOBAL = "fsm-global";

  public static int handleHash(Class<?> state, Class<?> event, Object[] args) {
    Class<?>[] argTypes = Arrays.stream(args)
        .map(Object::getClass)
        .toArray(Class<?>[]::new);
    return handleHash(state, ObjectArrays.concat(event, argTypes));
  }

  public static int handleHash(Class<?> stateType, Class<?>[] argTypes) {
    return Objects.hash(stateType, argTypes);
  }

  public static MethodHandle determineHandler(Map<Integer, MethodHandle> handlers, Enum state, Enum event, Object[] args) {
    MethodHandle handle = handlers.get(handleHash(state.getClass(), event.getClass(), args));
    if (handle == null) {
      handle = handlers.get(handleHash(NilState.class, event.getClass(), args));
    }

    requireNotNull(handle, "can't determine event handler for {}", event.getClass().getSimpleName());

    return handle;
  }

  public static MethodHandle makeMethodHandle(Method method) {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle handle;
    try {
      handle = lookup.unreflect(method);
    } catch (IllegalAccessException e) {
      log.error("can't unreflect method {}", method);
      throw new CrashedException("can't unreflect method", e);
    }
    return handle;
  }

  public enum NilState {}

  public static String generateActionId(String state, String event, Class<?>[] parameterTypes) {
    int hashCode = Arrays.stream(parameterTypes)
        .map(it -> Primitives.unwrap(it).getCanonicalName().hashCode())
        .reduce(0, (i1, i2) -> i1 + i2);
    return String.format("%s:%s:%d", state, event, hashCode);
  }

  public static String generateHandlerId(String from, String to, boolean post) {
    return String.format("%s:%s:%b", from, to, post);
  }

  public static String blankToGlobal(String state) {
    return Strings.isNullOrEmpty(state) ? STATE_GLOBAL : state;
  }
}
