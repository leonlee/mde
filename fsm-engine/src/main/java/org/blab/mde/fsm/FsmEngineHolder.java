package org.blab.mde.fsm;


public enum FsmEngineHolder {
  INSTANCE;

  private FsmEngine engine;

  public static FsmEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(FsmEngine engine) {
    INSTANCE.engine = engine;
  }
}
