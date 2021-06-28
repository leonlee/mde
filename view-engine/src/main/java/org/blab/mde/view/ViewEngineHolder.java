package org.blab.mde.view;


public enum ViewEngineHolder {
  INSTANCE;

  private ViewEngine engine;

  public static ViewEngine getEngine() {
    return INSTANCE.engine;
  }

  public static void setEngine(ViewEngine engine) {
    INSTANCE.engine = engine;
  }
}
