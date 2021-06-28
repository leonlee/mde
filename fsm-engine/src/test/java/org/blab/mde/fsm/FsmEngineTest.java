package org.blab.mde.fsm;

import org.junit.BeforeClass;
import org.junit.Test;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.fsm.demo.Car;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class FsmEngineTest {
  private static CompositeEngine engine;

  @BeforeClass
  public static void setup() {
    String compositePackage = "org.blab.mde.fsm.demo";
    engine = new CompositeEngine(compositePackage).start();
  }

  @Test
  public void oneTest() {
    Car car = engine.createOf(Car.class);
    assertNotNull(car);
    assertEquals(Car.States.Cold, car.currentState());

    car.start();
    assertEquals(Car.States.Started, car.currentState());

    car.upshift();
    assertEquals(Car.States.Running, car.currentState());
    assertEquals(10, car.monitor());

    car.upshift();
    assertEquals(Car.States.Running, car.currentState());
    assertEquals(20, car.monitor());

    car.upshift(2);
    assertEquals(Car.States.Running, car.currentState());
    assertEquals(20, car.monitor());

    assertTrue(car.rush(5, true));
    assertEquals(Car.States.Running, car.currentState());
    assertEquals(50, car.monitor());
  }

  @Test
  public void testException() {
    Car car = engine.createOf(Car.class);
    assertNotNull(car);
    assertEquals(Car.States.Cold, car.currentState());

    car.start();
    assertEquals(Car.States.Started, car.currentState());

    car.upshift();
    assertEquals(Car.States.Running, car.currentState());
    assertEquals(10, car.monitor());

    car.crashIt();
    assertEquals(Car.States.Crashed, car.currentState());
    assertEquals(0, car.getSpeed());

    car.fly();
    assertEquals(Car.States.Confused, car.currentState());
    assertEquals(0, car.getSpeed());
  }
}

