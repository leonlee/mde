package org.blab.mde.core.spring;

import org.springframework.context.ApplicationContext;

import static org.blab.mde.core.util.Guarder.requireNotNull;


public enum SpringHolder {
  INSTANCE;

  private ApplicationContext context;

  public static void setContext(ApplicationContext context) {
    requireNotNull(context);
    INSTANCE.context = context;
  }

  public static ApplicationContext getContext() {
    ApplicationContext context = INSTANCE.context;
    requireNotNull(context);

    return context;
  }

  public static <R> R getBean(Class<R> that) {
    return getContext().getBean(that);
  }

  public static <R> R getBean(String name) {
    //noinspection unchecked
    return (R) getContext().getBean(name);
  }
}
