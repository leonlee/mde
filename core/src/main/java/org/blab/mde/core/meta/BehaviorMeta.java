package org.blab.mde.core.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import lombok.Data;


@Data
public class BehaviorMeta implements MetaElement {
  private Method source;
  private List<Annotation> annotations;

  public BehaviorMeta(Method source) {
    this.source = source;
    this.annotations = Arrays.asList(source.getAnnotations());
  }


  @Override
  public void accept(MetaElementVisitor visitor) {
    visitor.visit(this);
  }
}
