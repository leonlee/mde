package org.blab.mde.core.meta;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.List;

import org.blab.mde.core.exception.CrashedException;

/**
 * <p> Base class of all composites. </p>
 */
public abstract class BaseComposite {
  public static final String INITIALIZER_NAME = "$mde$initializers";

  public BaseComposite() {
    List<Method> initializers = $mde$initializers();
    if (initializers == null) return;

    for (Method initializer : initializers) {
      try {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        lookup.unreflect(initializer).bindTo(this).invoke();
      } catch (Throwable throwable) {
        throw new CrashedException("can't invoke initializers {}", throwable, initializer.getName());
      }
    }
  }

  public List<Method> $mde$initializers() {
    return null;
  }
}
