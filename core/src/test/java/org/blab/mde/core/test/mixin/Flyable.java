package org.blab.mde.core.test.mixin;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;


@Mixin
public interface Flyable extends Moveable {
  @Property
  int getZ();

  void setZ(int z);

  void tryIt(int x);
//  default void tryIt(int x) {
//    System.out.println("trying " + Flyable.class.getCanonicalName());
//  }

  default void fly(int x, int y, int z) {
    setX(x);
    setY(y);
    setZ(z);
    Inner.tryPrivate();
  }

  class Inner {
    private static void tryPrivate() {

    }
  }
}
