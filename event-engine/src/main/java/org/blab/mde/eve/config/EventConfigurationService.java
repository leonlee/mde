package org.blab.mde.eve.config;

import com.google.common.collect.Lists;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.eve.EventEngine;
import org.blab.mde.eve.EventEngineHolder;
import org.blab.mde.eve.core.EventHandlerVisitor;


public class EventConfigurationService implements ConfigurationService {
  @Override
  public List<String> getPackages() {
    return Lists.newArrayList(EventEngine.class.getPackage().getName());
  }

  @Override
  public List<MetaElementBuilderVisitor> getBuilderVisitors() {
    return Lists.newArrayList(new EventHandlerVisitor());
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    EventEngine eventEngine = new EventEngine();
    EventEngineHolder.setEngine(eventEngine);

    return Lists.newArrayList(eventEngine);
  }

  @Override
  public int getPriority() {
    return 30;
  }
}
