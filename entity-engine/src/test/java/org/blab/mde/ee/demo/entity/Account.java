package org.blab.mde.ee.demo.entity;

import org.blab.mde.ee.annotation.Entity;
import org.blab.mde.ee.annotation.Field;
import org.blab.mde.ee.mixin.Auditable;
import org.blab.mde.ee.mixin.WithLongID;


@Entity(name = "account")
public interface Account extends WithLongID, Auditable {
  @Field
  String getName();

  void setName(String name);

  default void save() {
//    EventRod.postEvent(new BeforeSaveEvent(this));
  }
}
