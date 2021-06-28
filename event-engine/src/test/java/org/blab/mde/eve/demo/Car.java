package org.blab.mde.eve.demo;

import java.time.Instant;
import java.util.Date;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.eve.core.TypedEvent;
import org.blab.mde.eve.mixin.EventMixin;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Composite
public interface Car extends EventMixin {
  @Property
  boolean isStarted();

  void setStarted(boolean started);

  @Property
  Date getStartedOn();

  void setStartedOn(Date startedOn);

  default void start() {
    syncPost(new StartEvent(this, Date.from(Instant.now())));
  }

  default void stop() {
    asyncPost(new StopEvent(this, Date.from(Instant.now())));
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  class StartEvent extends TypedEvent<Car> {
    private Date startedOn;

    public StartEvent(Car source, Date startedOn) {
      super(source);
      this.startedOn = startedOn;
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  class StopEvent extends TypedEvent<Car> {
    private Date stoppedOn;

    public StopEvent(Car source, Date stoppedOn) {
      super(source);
      this.stoppedOn = stoppedOn;
    }
  }
}
