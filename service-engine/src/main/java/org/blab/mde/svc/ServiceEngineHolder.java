package org.blab.mde.svc;


public enum ServiceEngineHolder {
  INSTANCE;

  private ServiceEngine engine;

  public static ServiceEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(ServiceEngine engine) {
    INSTANCE.engine = engine;
  }
}
