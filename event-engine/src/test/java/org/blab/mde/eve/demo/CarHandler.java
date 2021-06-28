package org.blab.mde.eve.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.blab.mde.eve.annotation.EventHandler;
import org.blab.mde.eve.annotation.EventReceiver;


@EventReceiver
public interface CarHandler {
  Logger log = LoggerFactory.getLogger(CarHandler.class);

  @EventHandler
  default void onStart(Car.StartEvent event) {
    log.debug("car was started on {}", event.getStartedOn());
    event.getSource().setStarted(true);
    event.getSource().setStartedOn(event.getStartedOn());
  }

  @EventHandler
  default void onStop(Car.StopEvent event) {
    log.debug("car was stopped on {}", event.getStoppedOn());
    event.getSource().setStarted(false);
  }
}
