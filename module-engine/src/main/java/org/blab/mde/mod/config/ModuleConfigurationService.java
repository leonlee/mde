package org.blab.mde.mod.config;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.mod.ModuleEngine;
import org.blab.mde.mod.ModuleEngineHolder;

import static com.google.common.collect.Lists.newArrayList;


public class ModuleConfigurationService implements ConfigurationService {
  @Override
  public int getPriority() {
    return 60;
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    ModuleEngine engine = new ModuleEngine();
    ModuleEngineHolder.setEngine(engine);

    return newArrayList(engine);
  }
}
