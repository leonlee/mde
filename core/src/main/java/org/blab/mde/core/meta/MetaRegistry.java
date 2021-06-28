package org.blab.mde.core.meta;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.blab.mde.core.annotation.Extension;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.util.StreamUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireTrue;


@Data
@Slf4j
public class MetaRegistry {
  private SortedMap<Class<?>, CompositeMeta> mapping;
  private SortedMap<String, CompositeMeta> nameIndex;

  public MetaRegistry() {
    mapping = new TreeMap<>(Comparator.comparing(Class::getCanonicalName));
    nameIndex = new TreeMap<>();
  }

  public void populate(SortedSet<Class<?>> sources, SortedSet<Class<?>> extensions, SortedSet<Class<?>> mixins) {
    for (Class<?> source : sources) {
      Set<Class<?>> compositeMixins = extractMixins(source, mixins);
      SortedSet<Class<?>> compositeExtensions = extractExtensions(source, extensions);
      CompositeMeta meta = new CompositeMeta(source, compositeMixins, compositeExtensions);
      this.mapping.put(source, meta);
      indexName(meta);
    }
  }

  private void indexName(CompositeMeta meta) {
    String name = meta.getName();
    if (Strings.isNullOrEmpty(name)) {
      return;
    }

    if (nameIndex.containsKey(name)) {
      CompositeMeta existed = nameIndex.get(name);
      log.error("name {} was registered by {}, can't register as {} again!",
          name,
          existed.getSource().getCanonicalName(),
          meta.getSource().getCanonicalName());
      throw new CrashedException("name {} was duplicated", name);
    }

    nameIndex.put(name, meta);
  }

  public CompositeMeta find(Class<?> type) {
    requireTrue(mapping.containsKey(type), "type {} not found", type);
    return mapping.get(type);
  }

  public CompositeMeta find(String name) {
    requireNotBlank(name, "invalid composite name");
    requireTrue(nameIndex.containsKey(name), "name {} not found", name);
    return nameIndex.get(name);
  }

  public <T> Class<T> getCompositeType(Class<?> type) {
    //noinspection unchecked
    return (Class<T>) find(type).getType();
  }

  public <T> Class<T> getCompositeType(String name) {
    //noinspection unchecked
    return (Class<T>) find(name).getType();
  }

  public <T> Class<T> getCompositeSource(String name) {
    //noinspection unchecked
    return (Class<T>) find(name).getSource();
  }

  public String getCompositeName(Class<?> originType) {
    return find(originType).getName();
  }

  private SortedSet<Class<?>> extractExtensions(Class<?> compositeSource, SortedSet<Class<?>> extensions) {
    return extensions.stream()
        .filter(it -> isExtensionOf(compositeSource, it))
        .collect(StreamUtil.toTreeSet(ExtensionMeta::compareExtensionClass));
  }

  private static boolean isExtensionOf(Class<?> compositeSource, Class<?> extensionSource) {
    Extension annotation = ExtensionMeta.extensionOf(extensionSource);
    return annotation != null && annotation.source().equals(compositeSource);
  }

  private Set<Class<?>> extractMixins(Class<?> composite, SortedSet<Class<?>> mixins) {
    return Arrays.stream(composite.getInterfaces())
        .filter(mixins::contains)
        .collect(Collectors.toSet());
  }
}
