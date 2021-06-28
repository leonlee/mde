package org.blab.mde.ee;


public enum EntityEngineHolder {
  INSTANCE;

  private EntityEngine engine;

  public static EntityEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(EntityEngine engine) {
    INSTANCE.engine = engine;
  }

}
