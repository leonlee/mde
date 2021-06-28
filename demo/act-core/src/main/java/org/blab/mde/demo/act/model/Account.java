package org.blab.mde.demo.act.model;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.mixin.Printable;
import org.blab.mde.demo.act.dal.AccountRepository;
import org.blab.mde.demo.act.model.mixin.CodeMixin;
import org.blab.mde.demo.act.model.mixin.UserMixin;
import org.blab.mde.ee.annotation.Aggregate;
import org.blab.mde.ee.annotation.Field;
import org.blab.mde.ee.mixin.Auditable;
import org.blab.mde.ee.mixin.WithLongID;
import org.blab.mde.eve.spring.SpringEventMixin;


@Aggregate(Account.NAME)
@Scope("prototype")
public interface Account
    extends WithLongID, Auditable, UserMixin, CodeMixin, Printable, SpringEventMixin {

  String NAME = "actAccount";

  @Field
  String getName();

  void setName(String name);

  @Field
  AccountType getType();

  void setType(AccountType type);

  @Field
  long getParentId();

  void setParentId(long parentId);

  @Field
  long getBalance();

  void setBalance(long balance);


  @Property
  @Inject
  AccountRepository getRepo();

  void setRepo(AccountRepository repo);

  @Transactional
  default Account save() {
    return getRepo().save(this);
  }
}
