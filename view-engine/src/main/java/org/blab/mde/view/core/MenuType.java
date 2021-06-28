package org.blab.mde.view.core;


public enum MenuType {
  TOOLBAR,
  CONTEXT;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
