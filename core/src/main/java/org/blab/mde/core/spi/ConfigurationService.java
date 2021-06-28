package org.blab.mde.core.spi;

import java.util.Collections;
import java.util.List;

import org.blab.mde.core.CompositeEngineListener;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;

public interface ConfigurationService extends Comparable<ConfigurationService> {
  default List<String> getPackages() {
    return Collections.emptyList();
  }

  default List<MetaElementBuilderVisitor> getBuilderVisitors() {
    return Collections.emptyList();
  }

  default List<CompositeEngineListener> getListeners() {
    return Collections.emptyList();
  }

  default ClassLoader getClassLoader() {
    return null;
  }

  /**
   * Provide priority for providers, at present it only affect class loader.
   * Warning: don't set same priority for different provider, it will be overridden in SortedSet.
   *
   * @return priority
   */
  int getPriority();

  @Override
  default int compareTo(ConfigurationService that) {
    return Integer.compare(this.getPriority(), that.getPriority());
  }
}
