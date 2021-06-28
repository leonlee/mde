package org.blab.mde.mod.test2;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Property;


@Composite
public interface Mt2Composite {
  @Property
  String getName();

  void setName(String name);
}
