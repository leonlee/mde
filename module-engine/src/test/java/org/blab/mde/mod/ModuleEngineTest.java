package org.blab.mde.mod;

import org.junit.BeforeClass;
import org.junit.Test;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.mod.test1.Mt1Composite;
import org.blab.mde.mod.test2.Mt2Composite;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



public class ModuleEngineTest {
  private static CompositeEngine engine;

  @BeforeClass
  public static void init() {
    engine = new CompositeEngine().start();
  }

  @Test
  public void moduleTest() {
    ModuleEngine moduleEngine = ModuleEngineHolder.getEngine();
    assertEquals(2, moduleEngine.getModules().size());

    IModule mod1 = moduleEngine.getModules().get("mod1");
    assertNotNull(mod1);
    assertTrue(engine.getBasePackages().contains("org.blab.mde.mod.test1"));
    assertTrue(engine.getBasePackages().containsAll(newArrayList(mod1.packages())));

    IModule mod2 = moduleEngine.getModules().get("mod2");
    assertNotNull(mod2);
    assertTrue(engine.getBasePackages().contains("org.blab.mde.mod.test2"));
    assertTrue(engine.getBasePackages().containsAll(newArrayList(mod2.packages())));

    Mt1Composite mt1 = engine.create(Mt1Composite.class);
    assertNotNull(mt1);

    mt1.setName("mt1c");
    assertEquals("mt1c", mt1.getName());

    Mt2Composite mt2 = engine.create(Mt2Composite.class);
    assertNotNull(mt2);

    mt2.setName("mt2c");
    assertEquals("mt2c", mt2.getName());
  }
}
