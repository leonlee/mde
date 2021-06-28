package org.blab.mde.eve;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EventEngine implements CompositeEngineListener {
  public static final String SYNC_EVENT_BUS = "sync_event_bus";
  public static final String ASYNC_EVENT_BUS = "async_event_bus";

  @Getter
  private EventBus eventBus;
  @Getter
  private AsyncEventBus asyncEventBus;

  private CompositeEngine compositeEngine;

  public EventEngine() {
    eventBus = new EventBus(SYNC_EVENT_BUS);

    int processors = Runtime.getRuntime().availableProcessors();
    int parallelism = processors > 8 ? 8 : processors - 1;
    asyncEventBus = new AsyncEventBus(ASYNC_EVENT_BUS, Executors.newWorkStealingPool(parallelism));
  }

  public void register(Class<?> type) {
    Object handler = compositeEngine.create(type);
    log.debug("registering handler: {}", handler);

    eventBus.register(handler);
    asyncEventBus.register(handler);
  }

  @Override
  public void afterStart(CompositeEngineContext context, CompositeEngine engine) {
    this.compositeEngine = engine;
  }

  public void unregister(Class<?> listener) {
    eventBus.unregister(listener);
    asyncEventBus.unregister(listener);
  }
}
