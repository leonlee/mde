package org.blab.mde.ee.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import org.blab.mde.ee.EntityEngine;
import org.blab.mde.ee.EntityEngineHolder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@ConditionalOnClass
public class EntityEngineProcessor implements BeanFactoryPostProcessor {
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    beanFactory.registerSingleton(EntityEngine.class.getCanonicalName(), EntityEngineHolder.getEngine());
  }
}
