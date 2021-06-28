package org.blab.mde.core.test.mixin;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;


@Mixin
public interface Descriptor {
  @Property
  String getDescription();

  void setDescription(String description);

  @Initializer
  default void initDescription() {
    setDescription("na.");
  }
}
