package org.blab.mde.core.meta;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.util.Guarder;
import org.blab.mde.core.util.MixinUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static java.util.Arrays.asList;


@Data
@Slf4j
public class PropertyMeta implements MetaElement {
  private Method source;
  private String name;
  private Type type;
  private List<Annotation> annotations;

  public PropertyMeta(Method source) {
    Guarder.requireNotNull(source, "null property source method");

    log.debug("building property of {}", source);

    this.source = source;
    this.name = MixinUtil.propertyOf(source);
    this.type = MixinUtil.typeOfProperty(source);
    this.annotations = Arrays.stream(source.getDeclaredAnnotations())
        .filter(PropertyMeta::isSupportedAnnotation)
        .collect(Collectors.toList());
  }

  private static boolean isSupportedAnnotation(Annotation annotation) {
    Class<? extends Annotation> type = annotation.annotationType();
    if (Property.class.isAssignableFrom(type)) {
      return false;
    }

    Target target = type.getDeclaredAnnotation(Target.class);
    if (target == null) {
      return true;
    }

    boolean supported = asList(target.value()).contains(ElementType.FIELD);

    if (!supported) {
      log.warn("encounter unsupported annotation {}", annotation);
    }

    return supported;
  }

  @Override
  public void accept(MetaElementVisitor visitor) {
    visitor.visit(this);
  }
}
