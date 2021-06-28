package org.blab.mde.core.spi;

import com.google.common.collect.Lists;

import java.util.List;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineHolder;
import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;


public class DefaultConfigurationService implements ConfigurationService {
  @Override
  public List<String> getPackages() {
    return Lists.newArrayList(CompositeEngine.class.getPackage().getName());
  }

  @Override
  public List<MetaElementBuilderVisitor> getBuilderVisitors() {
    return Lists.newArrayList(new MetaElementBuilderVisitor() {
    });
  }

  @Override
  public List<CompositeEngineListener> getListeners() {
    CompositeEngineListener listener = new CompositeEngineListener() {
      @Override
      public void afterStart(CompositeEngineContext context, CompositeEngine engine) {
        CompositeEngineHolder.setEngine(engine);
      }
    };

    return Lists.newArrayList(listener);
  }

  @Override
  public int getPriority() {
    return 1;
  }
}
