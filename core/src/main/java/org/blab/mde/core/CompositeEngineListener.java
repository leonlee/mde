package org.blab.mde.core;


public interface CompositeEngineListener {
  default void afterStart(CompositeEngineContext context, CompositeEngine engine) {
  }

  default void afterNewComposite(CompositeEngineContext context, Object instance) {
  }

  default void afterValidate(CompositeEngineContext context, CompositeEngine engine) {
  }
}
