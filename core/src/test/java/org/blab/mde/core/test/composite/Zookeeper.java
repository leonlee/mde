package org.blab.mde.core.test.composite;

import org.apache.commons.lang3.builder.ToStringStyle;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.mixin.Printable;


@Composite(scope = CompositeType.SCOPE_SINGLETON)
public interface Zookeeper extends Printable {
  @Property
  String getName();

  void setName(String name);

  @Property
  boolean isMale();

  void setMale(boolean male);

  @Override
  default String[] getExcludedPrintableFields() {
    return new String[]{"male"};
  }

  @Override
  default ToStringStyle getToStringStyle() {
    return ToStringStyle.NO_CLASS_NAME_STYLE;
  }
}
