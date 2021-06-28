package org.blab.mde.core;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;

import org.blab.mde.core.meta.AnnotationRegistry;
import org.blab.mde.core.meta.BuilderVisitorSet;
import org.blab.mde.core.meta.MetaRegistry;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CompositeEngineContext {
  @Builder.Default
  private final Map<String, Object> attributes = new ConcurrentHashMap<>();
  private SortedSet<String> basePackages;
  private MetaRegistry metaRegistry;
  private AnnotationRegistry annotationRegistry;
  private BuilderVisitorSet builderVisitors;
  private ClassLoader classLoader;
  private Set<CompositeEngineListener> engineListeners;
  private CompositeFactory compositeFactory;
  private CompositeEngineProperties properties;

  public boolean hasAttribute(String name) {
    return attributes.containsKey(name);
  }

  public <R> R getAttribute(String name) {
    //noinspection unchecked
    return (R) attributes.get(name);
  }

  public void setAttribute(String name, Object object) {
    attributes.put(name, object);
  }

  public String[] getAttributeNames() {
    return attributes.keySet().toArray(new String[attributes.size()]);
  }
}
