package org.blab.mde.view.core;


public enum MenuItemType {
  SPLITTER,
  VIEW,
  POPUP,
  GROUP;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
