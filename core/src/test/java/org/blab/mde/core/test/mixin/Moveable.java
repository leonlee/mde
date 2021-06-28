package org.blab.mde.core.test.mixin;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;


@Mixin
public interface Moveable {
  @Property
  int getX();

  void setX(int x);

  @Property
  int getY();

  void setY(int y);

  @Initializer
  default void initMove() {
    setX(1);
    setY(1);
  }

  default void moveTo(Integer x, Integer y) {
    setX(x);
    setY(y);
  }

  default void reset() {
    setX(0);
    setY(0);
  }
}
