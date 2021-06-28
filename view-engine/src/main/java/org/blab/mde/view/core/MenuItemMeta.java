package org.blab.mde.view.core;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MenuItemMeta extends ElementMeta<MenuItemType, MenuItemMeta> implements Serializable {
  private static final long serialVersionUID = 8076974379359701653L;

  @Override
  Component getComponent() {
    return Component.MENU_ITEM;
  }
}
