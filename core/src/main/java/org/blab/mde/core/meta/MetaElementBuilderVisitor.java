package org.blab.mde.core.meta;

import net.bytebuddy.dynamic.DynamicType.Builder;

import org.blab.mde.core.CompositeEngineContext;

public interface MetaElementBuilderVisitor {
  default Builder<BaseComposite> visit(CompositeEngineContext context, Builder<BaseComposite> builder, CompositeMeta element) {
    return builder;
  }

  /**
   * Visit element after loaded enhanced composite type.
   *
   * @param context context for accessing composite engine
   * @param element composite element
   */
  default void visit(CompositeEngineContext context, CompositeMeta element) {
  }

  default Builder<BaseComposite> visit(CompositeEngineContext context, Builder<BaseComposite> builder, ExtensionMeta element) {
    return builder;
  }

  default Builder<BaseComposite> visit(CompositeEngineContext context, Builder<BaseComposite> builder, MixinMeta element) {
    return builder;
  }

  default Builder<BaseComposite> visit(CompositeEngineContext context, Builder<BaseComposite> builder, PropertyMeta element) {
    return builder;
  }

  default Builder<BaseComposite> visit(CompositeEngineContext context, Builder<BaseComposite> builder, BehaviorMeta element) {
    return builder;
  }
}
