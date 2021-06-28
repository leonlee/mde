package org.blab.mde.ee.demo.entity;

import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.annotation.Property;


@Extension(source = Account.class)
public interface AccountExt extends Account {
  @Property
  String getChartType();

  void setChartType(String chartType);

  default String ping() {
    return "pong from account ext";
  }
}
