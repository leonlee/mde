package org.blab.mde.core.test.composite;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.annotation.Priorities;
import org.blab.mde.core.annotation.Self;
import org.blab.mde.core.test.mixin.Nameable;

import static org.blab.mde.core.annotation.Delegate.BindingMode.APPEND;


@Extension(source = Cow.class, priority = Priorities.PRIORITY_P8)
public interface SeaCow {
  @Delegate(name = "greeting", parameterTypes = {})
  static String greeting(@Self Nameable self) {
    return String.format("%s: gugugu ...", self.getName());
  }

  @Delegate(name = "makeFriends", parameterTypes = {}, mode = APPEND, source = Cow.class)
  static void makeFriends(@Self Cow self) {
    self.getFriends().add("kitty");
  }
}

