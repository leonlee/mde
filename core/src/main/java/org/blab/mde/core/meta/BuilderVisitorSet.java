package org.blab.mde.core.meta;

import net.bytebuddy.dynamic.DynamicType;

import java.util.HashSet;

import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.exception.CrashedException;


public class BuilderVisitorSet extends HashSet<MetaElementBuilderVisitor> implements MetaElementBuilderVisitor {
  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, CompositeMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      try {
        builder = visitor.visit(context, builder, element);
      } catch (IllegalStateException e) {
        if (e.getMessage().contains("Cannot call super (or default) method ")) {
          throw new CrashedException("don't extends the source type of the delegate in append mode, extract delegate to other Extension/Mixin");
        }
        throw e;
      }
    }
    return builder;
  }

  @Override
  public void visit(CompositeEngineContext context, CompositeMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      visitor.visit(context, element);
    }
  }

  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, ExtensionMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      builder = visitor.visit(context, builder, element);
    }
    return builder;
  }

  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, MixinMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      builder = visitor.visit(context, builder, element);
    }
    return builder;
  }

  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, PropertyMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      builder = visitor.visit(context, builder, element);
    }
    return builder;
  }

  @Override
  public DynamicType.Builder<BaseComposite> visit(CompositeEngineContext context, DynamicType.Builder<BaseComposite> builder, BehaviorMeta element) {
    for (MetaElementBuilderVisitor visitor : this) {
      builder = visitor.visit(context, builder, element);
    }
    return builder;
  }
}
