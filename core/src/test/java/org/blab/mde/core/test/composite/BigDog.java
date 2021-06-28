package org.blab.mde.core.test.composite;

import org.blab.mde.core.annotation.Composite;


@Composite("bigDog")
public interface BigDog extends Dog {
  @Override
  default String greeting() {
    return String.format("hello: Big %s", getName());
  }
}
