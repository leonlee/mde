package org.blab.mde.fsm.config;

import com.google.common.collect.Lists;

import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.core.spi.ConfigurationService;
import org.blab.mde.fsm.FsmEngine;
import org.blab.mde.fsm.FsmEngineHolder;
import org.blab.mde.fsm.core.FsmBuilderVisitor;


public class FsmConfigurationService implements ConfigurationService {
  @Override
  public List<String> getPackages() {
    return Lists.newArrayList(FsmEngine.class.getPackage().getName());
  }

  @Override
  public List<MetaElementBuilderVisitor> getBuilderVisitors() {
    return Lists.newArrayList(new FsmBuilderVisitor());
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    FsmEngine fsmEngine = new FsmEngine();
    FsmEngineHolder.setEngine(fsmEngine);
    return Lists.newArrayList(fsmEngine);
  }

  @Override
  public int getPriority() {
    return 40;
  }
}
