package org.blab.mde.core.meta;


public interface MetaElementVisitor {
  void visit(CompositeMeta element);

  void visit(ExtensionMeta element);

  void visit(MixinMeta element);

  void visit(PropertyMeta element);

  void visit(BehaviorMeta element);
}
