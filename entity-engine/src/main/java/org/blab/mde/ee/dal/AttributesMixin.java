package org.blab.mde.ee.dal;

import com.google.common.collect.ArrayListMultimap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;

import static org.blab.mde.core.util.Guarder.requireNotEmpty;


@Mixin
public interface AttributesMixin {
  @Property
  ArrayListMultimap<String, Object> getAttributes();

  void setAttributes(ArrayListMultimap<String, Object> attributes);

  @Initializer
  default void initAttributesMixin() {
    setAttributes(ArrayListMultimap.create());
  }

  default AttributesMixin addAttribute(String name, Object... values) {
    requireNotEmpty(values);
    Arrays.stream(values)
        .forEach(it -> getAttributes().put(name, it));

    return this;
  }

  default List<Object> getAttribute(String name) {
    return getAttributes().get(name);
  }

  default Optional<Object> getFirstAtribute(String name) {
    return getAttributes().get(name).stream().findFirst();
  }
}
