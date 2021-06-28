package org.blab.mde.mod.test1;

import org.blab.mde.mod.IModule;


public class ModuleTest1 extends IModule.Base {
  @Override
  public String name() {
    return "mod1";
  }

  @Override
  public String[] packages() {
    return new String[]{ModuleTest1.class.getPackage().getName()};
  }
}
