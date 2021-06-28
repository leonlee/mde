package org.blab.mde.core.test.composite;

import org.blab.mde.core.annotation.*;
import org.blab.mde.core.mixin.Composable;
import org.blab.mde.core.test.mixin.Moveable;
import org.blab.mde.core.test.mixin.Nameable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.blab.mde.core.test.composite.Cow.Cask.STATE;


@Composite
public interface Cow extends Composable, Nameable, Moveable, Serializable {
  @Property
  String getColor();

  void setColor(String color);

  @Property
  Cask getCask();

  void setCask(Cask cask);

  @Property
  List<String> getFriends();

  void setFriends(List<String> friends);

  @Initializer
  default void init() {
    setColor("red");
    setCask(new Cask("test"));
    setFriends(new ArrayList<>());
  }

  @Override
  default String greeting() {
    STATE.hg();
    STATE.test = "aaa";
    getCask().hg();
    return String.format("hello: Mr %s", getName());
  }

  default void makeFriends() {
    getFriends().add("puppy");
  }

  default String cowsay() {
    return String.format("%s: [[I'm %s]]", getName(), getColor());
  }

  @Delegate(name = "toString", parameterTypes = {})
  static String cowToString(@Self Cow self) {
    return self.getColor();
  }

  @Delegate(name = "hashCode", parameterTypes = {})
  static int cowHashCode(@Self Cow self) {
    return self.getColor().hashCode();
  }

  class Cask {
    static Cask STATE = new Cask("hah");
    private String test;

    private Cask(String test) {
      this.test = test;
    }

    private void hg() {
    }
  }
}
