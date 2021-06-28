package org.blab.mde.core.spring;

import com.google.common.collect.Lists;

import java.util.List;

import org.blab.mde.core.spi.ConfigurationService;


public class SpringConfigurationService implements ConfigurationService {

  @Override
  public List<String> getPackages() {
    return Lists.newArrayList(State.DEFAULT.getPackages());
  }

  @Override
  public ClassLoader getClassLoader() {
    return getClass().getClassLoader();
  }

  @Override
  public int getPriority() {
    return 10;
  }

  public enum State {
    DEFAULT;
    private String[] packages = new String[0];

    public String[] getPackages() {
      return packages;
    }

    public void setPackages(String[] packages) {
      this.packages = packages;
    }
  }
}
