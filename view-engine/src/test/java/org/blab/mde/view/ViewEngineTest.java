package org.blab.mde.view;

import org.junit.BeforeClass;
import org.junit.Test;

import org.blab.mde.core.CompositeEngine;



public class ViewEngineTest {
  private static CompositeEngine engine;

  @BeforeClass
  public static void init() {
    engine = new CompositeEngine().start();
  }

  @Test
  public void viewTest() {

  }
}
