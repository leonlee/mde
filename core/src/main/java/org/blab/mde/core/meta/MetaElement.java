package org.blab.mde.core.meta;


public interface MetaElement {
  void accept(MetaElementVisitor visitor);
}
