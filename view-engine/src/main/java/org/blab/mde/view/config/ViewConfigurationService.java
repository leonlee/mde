package org.blab.mde.view.config;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.view.ViewEngine;
import org.blab.mde.view.ViewEngineHolder;

import static com.google.common.collect.Lists.newArrayList;


public class ViewConfigurationService implements ConfigurationService {
  @Override
  public int getPriority() {
    return 50;
  }

  @Override
  public List<String> getPackages() {
    return newArrayList(ViewEngine.class.getPackage().getName());
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    ViewEngine engine = new ViewEngine();
    ViewEngineHolder.setEngine(engine);

    return newArrayList(engine);
  }
}
