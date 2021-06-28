package org.blab.mde.ee.dal;

import com.google.common.collect.ImmutableMap;

import org.blab.mde.core.annotation.Composite;


@Composite
public interface DefaultQuery extends QueryMixin<ImmutableMap<String, ?>> {
  @Override
  default ImmutableMap<String, ?> createCriteria() {
    return ImmutableMap.of();
  }
}
