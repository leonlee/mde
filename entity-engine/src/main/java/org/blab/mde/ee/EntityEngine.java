package org.blab.mde.ee;


import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.ee.annotation.Aggregate;
import org.blab.mde.ee.annotation.Entity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireTrue;


@Slf4j
public class EntityEngine implements CompositeEngineListener {
  @Getter
  private CompositeEngine compositeEngine;

  @Override
  public void afterStart(CompositeEngineContext context, CompositeEngine engine) {
    compositeEngine = engine;
  }

  public <T> T createOf(Class<T> type) {
    return compositeEngine.createOf(type);
  }

  public <T> T create(Class<?> type) {
    return compositeEngine.create(type);
  }

  public <T> Class<T> typeOf(Class<T> originType) {
    return compositeEngine.typeOf(originType);
  }

  public <T> Class<T> typeOf(String name) {
    return compositeEngine.typeOf(name);
  }

  public String nameOf(Class<?> originType) {
    return compositeEngine.nameOf(originType);
  }

  public <T> Class<T> sourceOf(String name) {
    return compositeEngine.sourceOf(name);
  }

  public <T> T newEntity(Class<?> type) {
    requireTrue(type.getAnnotation(Entity.class) != null
        || type.getAnnotation(Aggregate.class) != null, "invalid entity {}", type.getCanonicalName());
    return compositeEngine.create(type);
  }
}
