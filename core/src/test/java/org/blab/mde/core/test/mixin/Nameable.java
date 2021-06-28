package org.blab.mde.core.test.mixin;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Modifier;
import org.blab.mde.core.annotation.Property;


@Mixin
public interface Nameable extends Descriptor {
  @Property
  @Modifier(Modifier.VOLATILE)
  @Modifier(Modifier.TRANSIENT)
  String getName();

  void setName(String name);

  @Initializer
  default void initName() {
    setName("Mr None");
  }

  default String greeting() {
    return String.format("hello: %s", getName());
  }
}
