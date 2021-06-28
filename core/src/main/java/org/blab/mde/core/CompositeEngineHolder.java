package org.blab.mde.core;


public enum CompositeEngineHolder {
  INSTANCE;
  private CompositeEngine engine;

  public static CompositeEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(CompositeEngine engine) {
    INSTANCE.engine = engine;
  }
}
