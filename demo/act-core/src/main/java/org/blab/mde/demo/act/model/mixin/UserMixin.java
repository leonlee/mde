package org.blab.mde.demo.act.model.mixin;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.ee.annotation.Field;


@Mixin
public interface UserMixin {
  @Field
  long getUid();

  void setUid(long uid);
}
