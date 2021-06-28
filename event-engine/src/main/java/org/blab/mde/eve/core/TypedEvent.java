package org.blab.mde.eve.core;


public class TypedEvent<T> {
  protected T source;

  public TypedEvent(T source) {
    this.source = source;
  }

  public T getSource() {
    return source;
  }

  public void setSource(T source) {
    this.source = source;
  }
}
