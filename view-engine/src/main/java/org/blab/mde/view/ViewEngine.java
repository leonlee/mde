package org.blab.mde.view;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineListener;


public class ViewEngine implements CompositeEngineListener {
  private CompositeEngine compositeEngine;

  public <T> T lookup(Class<T> type) {
    return compositeEngine.createOf(type);
  }

  @Override
  public void afterStart(CompositeEngineContext context, CompositeEngine engine) {
    this.compositeEngine = engine;
  }
}
