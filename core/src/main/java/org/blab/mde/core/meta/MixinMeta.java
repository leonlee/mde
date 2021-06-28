package org.blab.mde.core.meta;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.util.Guarder;
import org.blab.mde.core.util.MixinUtil;
import lombok.Data;


@Data
public class MixinMeta implements MetaElement {
  protected Class<?> source;
  protected Set<PropertyMeta> properties;
  protected Set<BehaviorMeta> behaviors;
  protected Set<DelegateMeta> delegates;


  public MixinMeta(Class<?> source) {
    Guarder.requireNotNull(source);
    Guarder.requireTrue(source.isInterface(), "mixin {} is not interface", source.getCanonicalName());

    this.source = source;

    initProperties();
    initBehaviors();
    initDelegates();
  }

  private void initDelegates() {
    this.delegates = MethodUtils.getMethodsListWithAnnotation(source, Delegate.class)
        .stream()
        .map(DelegateMeta::new)
        .collect(Collectors.toSet());
  }

  protected void initBehaviors() {
    this.behaviors = Arrays.stream(source.getMethods())
        .filter(MixinUtil::isBehavior)
        .map(BehaviorMeta::new)
        .collect(Collectors.toSet());
  }

  protected void initProperties() {
    this.properties = Arrays.stream(source.getMethods())
        .filter(MixinUtil::isProperty)
        .map(PropertyMeta::new)
        .collect(Collectors.toSet());
  }

  @Override
  public void accept(MetaElementVisitor visitor) {
    visitor.visit(this);
  }
}
