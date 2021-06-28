package org.blab.mde.demo.act.model;

import javax.inject.Inject;

import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.spring.SpringHolder;
import org.blab.mde.demo.act.dal.ContactRepository;
import org.blab.mde.ee.annotation.Entity;
import org.blab.mde.ee.annotation.Field;
import org.blab.mde.ee.mixin.Auditable;
import org.springframework.context.annotation.Scope;


@Entity
@Scope("prototype")
public interface Contact extends Auditable {

  @Field
  String getName();
  void setName(String name);

  @Field
  String getMobile();
  void setMobile(String mobile);

  @Field
  String getQq();
  void setQq(String qq);

  @Field
  String getAddress();
  void setAddress(String address);

  @Property
  @Inject
  ContactRepository getRepository();
  void setRepository(ContactRepository repository);

  default boolean save() {
    return getRepository().save(this) != 0;
  }

  default boolean update() {
    return getRepository().update(this) == 1;
  }

  static Contact selectById(long id) {
    return SpringHolder.getBean(ContactRepository.class).selectById(id);
  }
}
