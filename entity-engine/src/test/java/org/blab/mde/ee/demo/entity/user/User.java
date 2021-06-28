package org.blab.mde.ee.demo.entity.user;

import org.blab.mde.ee.annotation.Entity;
import org.blab.mde.ee.annotation.Field;


@Entity(name = "user")
public interface User {
  @Field
  String getUsername();

  void setUsername(String username);
}
