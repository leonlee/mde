package org.blab.mde.mod;

import com.google.common.base.MoreObjects;


public interface IModule {
  default String name() {
    return this.getClass().getName();
  }

  default String[] packages() {
    return new String[]{this.getClass().getPackage().getName()};
  }

  class Base implements IModule {
    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("name", name())
          .add("packages", packages())
          .toString();
    }
  }
}
