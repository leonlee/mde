package org.blab.mde.core.meta;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.blab.mde.core.annotation.CompositeDecorator;
import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.mixin.Composable;
import org.blab.mde.core.util.StreamUtil;
import org.blab.mde.core.annotation.ClassVer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.AnnotationUtil.getValue;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class CompositeMeta extends MixinMeta implements MetaElement {
  private Class<? extends BaseComposite> type;
  private List<Method> initializers;
  private Set<MixinMeta> mixins;
  private SortedSet<ExtensionMeta> extensions;
  private String name;
  private String scope;
  private Long classVer;
  private Class<CompositeDecorator> decorator;
  private Map<DelegateMeta, List<DelegateMeta>> delegateIndex;

  public CompositeMeta(Class<?> source, Set<Class<?>> mixinTypes, SortedSet<Class<?>> extensionTypes) {
    super(source);
    this.source = source;
    this.initializers = findInitializers(source);
    this.mixins = extractMixins(mixinTypes);
    this.mixins.add(new MixinMeta(Composable.class));
    this.extensions = extractExtensions(extensionTypes);
    this.delegateIndex = indexDelegates();

    Annotation annotation = extractAnnotation(source);
    this.name = extractName(annotation);
    this.scope = (String) getValue(annotation, CompositeType.ATTR_SCOPE);
    this.decorator = extractDecorator(annotation);
    this.classVer = extractClassVersion(source);
  }

  private Map<DelegateMeta, List<DelegateMeta>> indexDelegates() {
    List<DelegateMeta> delegateMetas = collectDelegates();
    return mergeDelegates(delegateMetas);
  }

  private Map<DelegateMeta, List<DelegateMeta>> mergeDelegates(List<DelegateMeta> delegateMetas) {
    Map<DelegateMeta, List<DelegateMeta>> metaListMap = new HashMap<>(delegateMetas.size());

    for (DelegateMeta meta : delegateMetas) {
      List<DelegateMeta> metaList = metaListMap.getOrDefault(meta, Lists.newArrayList());
      metaList.add(meta);
      Collections.sort(metaList);
      metaListMap.putIfAbsent(meta, metaList);
    }

    removeExclusions(metaListMap);

    return metaListMap;
  }

  private void removeExclusions(Map<DelegateMeta, List<DelegateMeta>> metaListMap) {
    metaListMap.forEach((delegateMeta, metaList) ->
        {
          for (DelegateMeta meta : metaList) {
            Iterator<DelegateMeta> iterator = metaList.iterator();
            while (iterator.hasNext()) {
              DelegateMeta it = iterator.next();
              Class<?> declaringClass = it.getTarget().getDeclaringClass();
              Comparator<Class<?>> comparator = Comparator.comparing(Class::getCanonicalName);
              boolean excluded = Arrays.binarySearch(meta.getExclusions(), declaringClass, comparator) != -1;

              if (excluded) {
                iterator.remove();
              }
            }
          }
        }
    );
  }

  private List<DelegateMeta> collectDelegates() {
    List<DelegateMeta> delegateMetas = new ArrayList<>(delegates);
    mixins.forEach(mixinMeta -> delegateMetas.addAll(mixinMeta.getDelegates()));
    extensions.forEach(extensionMeta -> delegateMetas.addAll(extensionMeta.getDelegates()));

    return delegateMetas;
  }

  private Long extractClassVersion(Class<?> source) {
    ClassVer annotation = source.getAnnotation(ClassVer.class);
    if (annotation == null) {
      return null;
    }

    return annotation.value();
  }

  private Class<CompositeDecorator> extractDecorator(Annotation annotation) {
    Object value = getValue(annotation, CompositeType.ATTR_DECORATOR);

    try {
      //noinspection unchecked
      Class<CompositeDecorator> decoratorClass = (Class<CompositeDecorator>) value;
      return decoratorClass;
    } catch (RuntimeException e) {
      throw new CrashedException("invalid composite decorator {}", value);
    }
  }

  private String extractName(Annotation annotation) {
    String name = (String) getValue(annotation);
    if (Strings.isNullOrEmpty(name)) {
      name = (String) getValue(annotation, CompositeType.ATTR_NAME);
    }
    return name;
  }

  private Annotation extractAnnotation(Class<?> source) {
    return Arrays.stream(source.getDeclaredAnnotations())
        .filter(it -> it.annotationType().getDeclaredAnnotation(CompositeType.class) != null)
        .findFirst()
        .orElseThrow(CrashedException::new);
  }

  private List<Method> findInitializers(Class<?> source) {
    List<Method> initializers = new ArrayList<>();

    initializers.addAll(MethodUtils.getMethodsListWithAnnotation(source, Initializer.class));

    Class<?>[] interfaces = source.getInterfaces();
    for (Class superInterface : interfaces) {
      initializers.addAll(findInitializers(superInterface));
    }

    return initializers;
  }

  private SortedSet<ExtensionMeta> extractExtensions(SortedSet<Class<?>> extensionTypes) {
    return extensionTypes.stream()
        .map(ExtensionMeta::new)
        .collect(StreamUtil.toTreeSet());
  }

  private Set<MixinMeta> extractMixins(Set<Class<?>> mixinTypes) {
    return mixinTypes.stream()
        .map(MixinMeta::new)
        .collect(Collectors.toSet());
  }

  @Override
  public void accept(MetaElementVisitor visitor) {
    visitor.visit(this);
  }
}
