package org.blab.mde.core.test.mixin;

import org.blab.mde.core.annotation.Mixin;


@Mixin
public interface Goable {
  void tryIt(int x);
//  default void tryIt(int x) {
//    System.out.println("trying " + Goable.class.getCanonicalName());
//  }
}
