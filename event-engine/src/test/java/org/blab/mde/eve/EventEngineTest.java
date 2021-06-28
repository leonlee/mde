package org.blab.mde.eve;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.eve.demo.Car;
import org.blab.mde.eve.demo.CarHandler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(BlockJUnit4ClassRunner.class)
public class EventEngineTest {

  private static CompositeEngine engine;

  @BeforeClass
  public static void init() {
    engine = new CompositeEngine().start();
  }

  @Test
  public void syncPostTest() throws InterruptedException {
    Car car = engine.createOf(Car.class);

    EventEngineHolder.getEngine().register(CarHandler.class);
    assertNotNull(car);

    car.start();
    assertTrue(car.isStarted());
    assertNotNull(car.getStartedOn());

    car.stop();
    Thread.sleep(500);
    assertFalse(car.isStarted());
  }
}

