package org.blab.mde.core;

import com.google.common.base.MoreObjects;

import java.util.HashMap;


public class CompositeEngineProperties extends HashMap<String, Object> {
  @Override
  public String toString() {
    MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
    entrySet().forEach(entry -> helper.add(entry.getKey(), entry.getValue()));

    return helper.toString();
  }
}
