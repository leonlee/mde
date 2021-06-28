package org.blab.mde.eve;


public enum EventEngineHolder {
  INSTANCE;

  private EventEngine engine;

  public static EventEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(EventEngine engine) {
    INSTANCE.engine = engine;
  }
}
