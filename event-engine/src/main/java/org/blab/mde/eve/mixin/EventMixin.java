package org.blab.mde.eve.mixin;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.eve.EventEngineHolder;
import org.blab.mde.eve.core.TypedEvent;


@Mixin
public interface EventMixin {
  default void syncPost(TypedEvent event) {
    EventEngineHolder.getEngine().getEventBus().post(event);
  }

  default void asyncPost(TypedEvent event) {
    EventEngineHolder.getEngine().getAsyncEventBus().post(event);
  }
}
