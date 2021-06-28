package org.blab.mde.core.spring;

import com.google.common.base.Strings;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

import org.blab.mde.core.CompositeEngineHolder;
import org.blab.mde.core.CompositeFactory;



@Component
public class SpringCompositeFactory implements ApplicationContextAware, CompositeFactory {
  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public ApplicationContext getContext() {
    return context;
  }

  @Override
  public <T> T newComposite(Class<?> type) {
    Objects.requireNonNull(getContext());

    String name = CompositeEngineHolder.getEngine().nameOf(type);
    if (Strings.isNullOrEmpty(name)) {
      //noinspection unchecked
      return (T) getContext().getBean((Class<?>) type);
    } else {
      //noinspection unchecked
      return (T) getContext().getBean(name);
    }
  }
}
