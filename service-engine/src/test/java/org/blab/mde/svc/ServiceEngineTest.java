package org.blab.mde.svc;

import org.junit.BeforeClass;
import org.junit.Test;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.svc.test.PingService;

import static org.blab.mde.svc.ServiceEngineHolder.getEngine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



public class ServiceEngineTest {
  private static CompositeEngine engine;

  @BeforeClass
  public static void init() {
    engine = new CompositeEngine().start();
  }

  @Test
  public void serviceTest() {
    PingService pingService = getEngine().lookup(PingService.class);
    assertNotNull(pingService);

    pingService = getEngine().lookup(PingService.class);
    assertNotNull(pingService);

    assertEquals(pingService.hashCode(), getEngine().lookup(PingService.class).hashCode());

    assertTrue(pingService.ping("127.0.0.1"));
  }
}
