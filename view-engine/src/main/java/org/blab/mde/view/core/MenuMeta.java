package org.blab.mde.view.core;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MenuMeta extends ElementMeta<MenuType, MenuMeta> implements Serializable {
  private static final long serialVersionUID = 5351711792129592645L;

  public MenuMeta() {
    type = MenuType.TOOLBAR;
  }

  @Override
  Component getComponent() {
    return Component.MENU;
  }
}
