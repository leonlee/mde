package org.blab.mde.core.test.composite;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.annotation.Priorities;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.annotation.Self;
import org.blab.mde.core.test.mixin.Flyable;
import org.blab.mde.core.test.mixin.Goable;
import org.blab.mde.core.test.mixin.Nameable;

import static org.blab.mde.core.annotation.Delegate.BindingMode.APPEND;


@Extension(source = Cow.class)
public interface FlyCow extends Goable, Flyable {
  @Property
  int getCode();

  void setCode(int code);

  default String broadcast() {
    return String.format("im %d", getCode());
  }

  default void tryIt(int x) {
    System.out.println("trying " + FlyCow.class.getCanonicalName());
  }

  @Delegate(name = "greeting", parameterTypes = {}, priority = Priorities.PRIORITY_P9)
  static String greeting(@Self Nameable self) {
    return String.format("%s: meow...", self.getName());
  }

  @Delegate(name = "makeFriends", parameterTypes = {}, mode = APPEND, exclusions = {})
  static void makeFriends(@Self Cow self) {
    self.getFriends().add("piggy");
  }
}
