package org.blab.mde.eve.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import org.blab.mde.eve.EventEngine;
import org.blab.mde.eve.EventEngineHolder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class EventEngineProcessor implements BeanFactoryPostProcessor {
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
    String name = EventEngine.class.getCanonicalName();
    factory.registerSingleton(name, EventEngineHolder.getEngine());

    log.debug("registered event engine {}", name);
  }
}
