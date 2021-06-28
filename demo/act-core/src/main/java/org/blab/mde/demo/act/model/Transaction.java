package org.blab.mde.demo.act.model;

import org.blab.mde.demo.act.model.mixin.CodeMixin;
import org.blab.mde.demo.act.model.mixin.UserMixin;
import org.blab.mde.ee.annotation.Entity;
import org.blab.mde.ee.annotation.Field;
import org.blab.mde.ee.mixin.Auditable;
import org.blab.mde.ee.mixin.WithLongID;


@Entity
public interface Transaction extends WithLongID, Auditable, UserMixin, CodeMixin {
  @Field
  TransactionType getType();

  void setType(TransactionType type);

  @Field
  String getDescription();

  void setDescription(String description);

  @Field
  long getAmount();

  void setAmount(long amount);
}
