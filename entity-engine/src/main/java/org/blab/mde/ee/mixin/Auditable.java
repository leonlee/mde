package org.blab.mde.ee.mixin;

import java.time.LocalDateTime;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.ee.annotation.Field;


@Mixin
public interface Auditable {
  @Field
  long getCreatedBy();

  void setCreatedBy(long uid);

  @Field
  LocalDateTime getCreatedOn();

  void setCreatedOn(LocalDateTime time);

  @Field
  long getUpdatedBy();

  void setUpdatedBy(long uid);

  @Field
  LocalDateTime getUpdatedOn();

  void setUpdatedOn(LocalDateTime time);

  @Field
  boolean isDeleted();

  void setDeleted(boolean deleted);

}
