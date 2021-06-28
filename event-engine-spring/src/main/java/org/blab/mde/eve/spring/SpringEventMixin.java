package org.blab.mde.eve.spring;

import org.springframework.beans.factory.annotation.Autowired;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.eve.EventEngine;
import org.blab.mde.eve.core.TypedEvent;


@Mixin
public interface SpringEventMixin {
  @Autowired
  @Property
  EventEngine getEventEngine();

  void setEventEngine(EventEngine eventEngine);


  default void syncPost(TypedEvent event) {
    getEventEngine().getEventBus().post(event);
  }

  default void asyncPost(TypedEvent event) {
    getEventEngine().getAsyncEventBus().post(event);
  }

  default void register(Class<?> listener) {
    getEventEngine().register(listener);
  }

  default void deregister(Class<?> listener) {
    getEventEngine().unregister(listener);
  }
}
