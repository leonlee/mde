package org.blab.mde.view.core;


public enum Component {
  MENU,
  MENU_ITEM;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
