package org.blab.mde.core.mixin;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Self;


@Mixin
public interface Printable {

  default String[] getExcludedPrintableFields() {
    return new String[0];
  }

  default ToStringStyle getToStringStyle() {
    return ToStringStyle.DEFAULT_STYLE;
  }

  @Delegate(name = "toString", parameterTypes = {})
  static String toString(@Self Printable self) {
    return new ReflectionToStringBuilder(self, self.getToStringStyle())
        .setExcludeFieldNames(self.getExcludedPrintableFields())
        .toString();
  }
}
