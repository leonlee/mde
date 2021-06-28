package org.blab.mde.demo.act.dal;

import org.junit.BeforeClass;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.ee.EntityEngine;
import org.blab.mde.ee.EntityEngineHolder;


public class BaseRepositoryTest {
  protected static EntityEngine engine;

  @BeforeClass
  public static void setup() {
    new CompositeEngine("org.blab.mde.demo.act").start();
    engine = EntityEngineHolder.getEngine();
  }
}
