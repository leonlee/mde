package org.blab.mde.core.meta;

import org.apache.commons.lang3.builder.CompareToBuilder;

import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.util.Guarder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExtensionMeta extends MixinMeta implements MetaElement, Comparable<ExtensionMeta> {
  private Extension extension;

  public ExtensionMeta(Class<?> source) {
    super(source);
    this.extension = extensionOf(source);
  }

  public static int priorityOf(ExtensionMeta meta) {
    Guarder.requireNotNull(meta);
    return meta.extension.priority();
  }

  public static Extension extensionOf(Class<?> type) {
    return type.getDeclaredAnnotation(Extension.class);
  }

  public static int getExtensionPriority(Class type) {
    Extension annotation = (Extension) type.getDeclaredAnnotation(Extension.class);
    return annotation.priority();
  }

  public static int compareExtensionClass(Class<?> it, Class<?> that) {
    return new CompareToBuilder()
        .append(getExtensionPriority(that), getExtensionPriority(it))
        .append(it.getCanonicalName(), that.getCanonicalName()).build();
  }

  @Override
  public void accept(MetaElementVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public int compareTo(ExtensionMeta that) {
    return compareExtensionClass(this.source, that.source);
  }
}
