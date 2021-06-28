package org.blab.mde.ee.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.blab.mde.core.spring.CompositeEngineAutoConfiguration;


@Configuration
@ComponentScan
@AutoConfigureAfter(CompositeEngineAutoConfiguration.class)
public class EntityEngineAutoConfiguration {
  @Autowired
  private EntityEngineProcessor processor;
}
