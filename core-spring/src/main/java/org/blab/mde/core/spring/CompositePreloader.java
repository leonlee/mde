package org.blab.mde.core.spring;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineHolder;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.spring.CompositeEngineProcessor.ROLE_COMPOSITE;



@Slf4j
@Component
public class CompositePreloader implements ApplicationListener<ApplicationReadyEvent> {
  public static final String SPRING_PRELOAD = "spring.preload";

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    CompositeEngine engine = CompositeEngineHolder.getEngine();
    Boolean isPreload = (Boolean) engine.getProperties().getOrDefault(SPRING_PRELOAD, false);

    if (isPreload) {
      ConfigurableApplicationContext context = event.getApplicationContext();
      Arrays.stream(context.getBeanDefinitionNames())
          .filter(name -> context.getBeanFactory().getBeanDefinition(name).getRole() == ROLE_COMPOSITE)
          .forEach(context::getBean);

      log.info("preload was enabled");
    } else {
      log.info("preload was disabled");
    }
  }
}
