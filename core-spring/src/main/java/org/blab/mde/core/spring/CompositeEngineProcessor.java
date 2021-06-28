package org.blab.mde.core.spring;

import com.google.common.base.Strings;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineHolder;
import org.blab.mde.core.CompositeEngineProperties;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.meta.CompositeMeta;
import org.blab.mde.core.spring.SpringConfigurationService.State;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;


@Slf4j
@Component
public class CompositeEngineProcessor implements BeanFactoryPostProcessor, EnvironmentAware, ApplicationContextAware {
  public static final String MDE_CORE_PACKAGES = "mde.core.packages";
  public static final String MDE_PROPERTIES = "mde.properties";
  public static final int ROLE_COMPOSITE = 5200;

  protected CompositeEngine compositeEngine;
  protected Environment environment;
  protected CompositeEngineProperties properties;

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    if (CompositeEngineHolder.getEngine() != null) {
      log.warn("refresh is not supported by composite engine only refresh bean registration");
      refresh(beanFactory);
      return;
    }

    loadProperties();
    initEngine(beanFactory);
    registerCompositeTypes(compositeEngine, beanFactory);

    log.debug("composite engine processor done");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringHolder.setContext(applicationContext);
  }

  protected void refresh(ConfigurableListableBeanFactory beanFactory) {
    compositeEngine = CompositeEngineHolder.getEngine();
    registerCompositeTypes(compositeEngine, beanFactory);
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  protected void initEngine(ConfigurableListableBeanFactory beanFactory) {
    compositeEngine = new CompositeEngine()
        .withProperties(properties)
        .withCompositeFactory(beanFactory.getBean(SpringCompositeFactory.class))
        .start();

    beanFactory.registerSingleton(CompositeEngine.class.getCanonicalName(), compositeEngine);
  }

  protected void registerCompositeTypes(CompositeEngine compositeEngine, ConfigurableListableBeanFactory beanFactory) {
    compositeEngine.getMetaRegistry()
        .getMapping()
        .forEach((__, meta) ->
            registerCompositeType(meta, beanFactory));
  }

  protected void registerCompositeType(CompositeMeta meta, ConfigurableListableBeanFactory factory) {
    String canonicalName = meta.getType().getCanonicalName();
    String name = Strings.isNullOrEmpty(meta.getName()) ? canonicalName : meta.getName();
    Class<?> type = meta.getType();
    Scope scope = type.getDeclaredAnnotation(Scope.class);

    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
    BeanDefinitionBuilder definitionBuilder = genericBeanDefinition(canonicalName);

    if (scope != null) {
      definitionBuilder.setScope(scope.value());
    } else {
      definitionBuilder.setScope(meta.getScope());
    }

    definitionBuilder.setRole(ROLE_COMPOSITE);

    //Must enable lazy initialization to resolve partial definitions loading.
    definitionBuilder.setLazyInit(true);

    registry.registerBeanDefinition(name, definitionBuilder.getBeanDefinition());

    log.debug("registered composite {}", name);
  }

  protected void loadProperties() {
    Binder binder = Binder.get(environment);

    String[] coreProperties = binder
        .bind(MDE_CORE_PACKAGES, String[].class)
        .orElseThrow(CrashedException::new);

    State.DEFAULT.setPackages(coreProperties);

    log.info("Loaded mde core coreProperties {}",
        ToStringBuilder.reflectionToString(coreProperties, ToStringStyle.MULTI_LINE_STYLE));

    properties = binder.bind(MDE_PROPERTIES, CompositeEngineProperties.class)
        .orElse(new CompositeEngineProperties());

    log.info("Loaded mde properties {}", properties);
  }
}
