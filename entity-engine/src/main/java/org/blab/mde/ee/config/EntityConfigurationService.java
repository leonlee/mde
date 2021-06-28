package org.blab.mde.ee.config;

import com.google.common.collect.Lists;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.ee.EntityEngine;
import org.blab.mde.ee.EntityEngineHolder;


public class EntityConfigurationService implements ConfigurationService {
  @Override
  public List<String> getPackages() {
    return Lists.newArrayList(EntityEngine.class.getPackage().getName());
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    EntityEngine entityEngine = new EntityEngine();
    EntityEngineHolder.setEngine(entityEngine);

    return Lists.newArrayList(entityEngine);
  }

  @Override
  public ClassLoader getClassLoader() {
    return null;
  }

  @Override
  public int getPriority() {
    return 20;
  }
}
