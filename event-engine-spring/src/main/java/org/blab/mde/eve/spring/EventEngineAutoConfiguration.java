package org.blab.mde.eve.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class EventEngineAutoConfiguration {
  @Autowired
  private EventEngineProcessor processor;
}
