package org.blab.mde.core.test.composite;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.mixin.Composable;
import org.blab.mde.core.test.mixin.Moveable;
import org.blab.mde.core.test.mixin.Nameable;


@Composite
public interface Cat extends Composable, Nameable, Moveable {
  default String meow() {
    return String.format("%s: %s", getName(), "mmm");
  }

  @Override
  default void moveTo(Integer x, Integer y) {
    setX(x + 1);
    setY(y + 1);
  }

  @Override
  default void reset() {
    setX(-1);
    setY(-1);
  }
}
