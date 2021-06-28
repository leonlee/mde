package org.blab.mde.fsm.core;


public class FsmEvent {
  private Object[] data;
  private Class<?> returnType;

  public static FsmEvent of(Object... args) {
    FsmEvent event = new FsmEvent();
    event.data = args;

    return event;
  }

  public FsmEvent expect(Class<?> returnType) {
    this.returnType = returnType;
    return this;
  }
}
