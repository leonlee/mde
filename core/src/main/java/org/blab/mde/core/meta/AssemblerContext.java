package org.blab.mde.core.meta;

import java.util.List;
import java.util.SortedSet;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder
@Data
public class AssemblerContext {
  @Getter
  private List<String> basePackages;
  @Getter
  private MetaRegistry metaRegistry;
  @Getter
  private AnnotationRegistry annotationRegistry;
  @Getter
  private SortedSet<Class<?>> composites;
  @Getter
  private SortedSet<Class<?>> extensions;
  @Getter
  private SortedSet<Class<?>> mixins;
}
