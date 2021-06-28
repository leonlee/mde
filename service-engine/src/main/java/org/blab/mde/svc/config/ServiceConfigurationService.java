package org.blab.mde.svc.config;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.svc.ServiceEngine;
import org.blab.mde.svc.ServiceEngineHolder;

import static com.google.common.collect.Lists.newArrayList;


public class ServiceConfigurationService implements ConfigurationService {
  @Override
  public int getPriority() {
    return 50;
  }

  @Override
  public List<String> getPackages() {
    return newArrayList(ServiceEngine.class.getPackage().getName());
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    ServiceEngine engine = new ServiceEngine();
    ServiceEngineHolder.setEngine(engine);

    return newArrayList(engine);
  }
}
