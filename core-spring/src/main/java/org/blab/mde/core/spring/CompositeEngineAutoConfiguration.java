package org.blab.mde.core.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CompositeEngineAutoConfiguration {
    @Autowired
    private CompositeEngineProcessor processor;

    @Autowired
    private CompositePreloader preloader;
}
