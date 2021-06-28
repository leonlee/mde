package org.blab.mde.mod.test2;

import org.blab.mde.mod.IModule;


public class ModuleTest2 extends IModule.Base {
  @Override
  public String name() {
    return "mod2";
  }

  @Override
  public String[] packages() {
    return new String[]{ModuleTest2.class.getPackage().getName()};
  }
}
