package org.blab.mde.fsm.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.fsm.annotation.FSM;
import org.blab.mde.fsm.annotation.FsmAction;
import org.blab.mde.fsm.annotation.FsmTransitionHandler;
import org.blab.mde.fsm.mixin.FsmMixin;

import static org.blab.mde.fsm.demo.Car.Events.Crash;
import static org.blab.mde.fsm.demo.Car.Events.Fly;
import static org.blab.mde.fsm.demo.Car.Events.Rush;
import static org.blab.mde.fsm.demo.Car.States.Running;


@FSM(states = Car.States.class, events = Car.Events.class)
public interface Car extends FsmMixin {
  Logger log = LoggerFactory.getLogger(Car.class);

  @Property
  int getSpeed();

  void setSpeed(int speed);

  @Property
  boolean isLightOn();

  void setLightOn(boolean lightOn);

  @Initializer
  default void init() {
    startFsm(States.Cold);
  }

  default void start() {
    send(Events.Ignite);
  }

  default void upshift() {
    send(Events.Upshift);
  }

  default void upshift(int gears) {
    send(Events.Upshift, gears);
  }

  default boolean rush(int gears, boolean lightOn) {
    return send(Rush, gears, lightOn);
  }

  default int monitor() {
    return send(Events.Monitor);
  }

  default void crashIt() {
    send(Crash);
  }

  default void fly() {
    send(Fly);
  }

  @FsmAction(state = States.Cold, event = Events.Ignite)
  default void _ignite() {
    setSpeed(0);
    moveTo(States.Started);
  }

  @FsmAction(state = States.Started, event = Events.Upshift)
  default void _upshiftInStarted() {
    setSpeed(10);
    moveTo(Running);
  }

  @FsmAction(state = States.Started, event = Events.SwitchOff)
  default void _switchOff() {
    setSpeed(0);
    moveTo(States.Cold);
  }

  @FsmAction(state = Running, event = Events.Upshift)
  default void _upshiftInRunning() {
    setSpeed(getSpeed() + 10);
  }

  @FsmAction(state = Running, event = Events.Upshift)
  default void _upshiftInRunning(int gears) {
    setSpeed(10 * gears);
  }

  @FsmAction(state = Running, event = Rush)
  default boolean _rushInRunning(int gears, boolean lightOn) {
    setSpeed(10 * gears);
    setLightOn(true);

    return isLightOn();
  }

  @FsmAction(state = Running, event = Events.Downshift)
  default void _downshiftInRunning() {
    int speed = getSpeed() - 10;
    if (speed <= 0) {
      setSpeed(0);
      moveTo(States.Started);
    } else {
      setSpeed(speed);
      moveTo(Running);
    }
  }

  @FsmAction(state = Running, event = Crash)
  default void _testCrash() {
    throw new IllegalArgumentException("crashed");
  }

  @FsmAction(event = Events.Monitor)
  default int _monitorSpeed() {
    return getSpeed();
  }

  @FsmTransitionHandler(from = States.Cold, to = States.Started)
  default void _beforeIgnition() {
    log.warn("go go go");
  }

  @FsmTransitionHandler(from = States.Cold, to = States.Started, after = true)
  default void _afterIgnition() {
    log.warn("yo yo yo");
  }

  @FsmTransitionHandler(after = false)
  default void _beforeTransition() {
    log.warn("transiting from {}", currentState());
  }

  @FsmTransitionHandler(after = true)
  default void _postTransition() {
    log.warn("transited to {}", currentState());
  }

  @Override
  default void handleException(Throwable e, Object[] args) {
    setSpeed(0);
    moveTo(States.Crashed);
  }

  @Override
  default void handleMismatchedEvent(String event, Object[] args) {
    setSpeed(0);
    moveTo(States.Confused);
  }

  interface States {
    String Cold = "cold";
    String Started = "started";
    String Running = "running";
    String Crashed = "crashed";
    String Confused = "confused";
  }

  interface Events {
    String Ignite = "ignite";
    String Upshift = "upshift";
    String Downshift = "downshift";
    String SwitchOff = "switchOff";
    String Monitor = "monitor";
    String Rush = "rush";
    String Crash = "crash";
    String Fly = "fly";
  }
}
