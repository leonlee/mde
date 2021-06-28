package org.blab.mde.ee;

import org.junit.BeforeClass;

import org.blab.mde.core.CompositeEngine;

public abstract class BaseEntityEngineTest {
  protected static EntityEngine engine;

  @BeforeClass
  public static void setup() {
    new CompositeEngine("org.blab.mde.ee.demo.entity")
        .start();

    engine = EntityEngineHolder.getEngine();
  }
}
