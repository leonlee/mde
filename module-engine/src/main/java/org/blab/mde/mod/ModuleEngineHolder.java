package org.blab.mde.mod;


public enum ModuleEngineHolder {
  INSTANCE;

  private ModuleEngine engine;

  public static ModuleEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(ModuleEngine engine) {
    INSTANCE.engine = engine;
  }
}
