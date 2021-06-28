package org.blab.mde.ee.core;

import org.blab.mde.ee.mixin.WithLongID;
import lombok.Data;


@Data
public class EntityMeta<T> {
  private Class<T> originType;
  private Class<? extends WithLongID> dynamicType;

  public EntityMeta(Class<T> entityType) {
    originType = entityType;
  }
}
