package org.blab.mde.view.home;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.annotation.Priorities;
import org.blab.mde.core.annotation.Self;

import static org.blab.mde.core.annotation.Delegate.BindingMode.OVERRIDE;


@Extension(source = HomePage.class, priority = Priorities.PRIORITY_MIN)
public interface HomeView {
  @Delegate(name = "assembleMenu", parameterTypes = {}, mode = OVERRIDE, source = HomePage.class)
  static void addMenu(@Self HomePage page) {
    page.getMenuMeta();
  }
}
