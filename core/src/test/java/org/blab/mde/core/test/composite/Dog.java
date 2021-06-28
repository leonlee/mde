package org.blab.mde.core.test.composite;

import java.io.Serializable;

import javax.inject.Inject;

import org.blab.mde.core.annotation.ClassVer;
import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.test.mixin.Moveable;
import org.blab.mde.core.test.mixin.Nameable;


@Composite("dog")
@ClassVer(2L)
public interface Dog extends Nameable, Moveable, Serializable {
  @Property
  @Inject
  Cat getCat();

  void setCat(Cat cat);

  @Property
  int getAge();

  void setAge(int age);

  @Initializer
  default void initDog() {
    setAge(2);
    setY(0);
  }

  default String getMaster() {
    return "leon";
  }


  default String bark() {
    return String.format("%s: www", getName());
  }


  @Override
  default String greeting() {
    return String.format("hello: Mr %s", getName());
  }


}
