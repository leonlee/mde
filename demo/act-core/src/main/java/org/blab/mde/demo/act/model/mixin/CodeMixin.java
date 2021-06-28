package org.blab.mde.demo.act.model.mixin;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.ee.annotation.Field;


@Mixin
public interface CodeMixin {
  @Field
  long getCode();

  void setCode(long code);
}
