package org.blab.mde.mod.test1;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Property;


@Composite
public interface Mt1Composite {
  @Property
  String getName();

  void setName(String name);
}
