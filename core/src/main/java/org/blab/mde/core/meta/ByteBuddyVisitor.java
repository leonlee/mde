package org.blab.mde.core.meta;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.FieldPersistence;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.annotation.Delegate.BindingMode;
import org.blab.mde.core.annotation.Modifier;
import org.blab.mde.core.mixin.Composable;
import org.blab.mde.core.util.CompositeUtil;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.collect.Sets.newHashSet;
import static org.blab.mde.core.annotation.CompositeDecorator.annotationsOf;
import static org.blab.mde.core.util.DelegateUtil.matcherOf;
import static org.blab.mde.core.util.DelegateUtil.parameterBinders;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static net.bytebuddy.implementation.MethodDelegation.withDefaultConfiguration;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultMethod;
import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;
import static org.apache.commons.lang3.ArrayUtils.addAll;


@Slf4j
public class ByteBuddyVisitor implements MetaElementVisitor {
  public static final String SERIAL_VERSION_UID = "serialVersionUID";
  public static final long DEFAULT_SERIAL_VER = 1L;

  private CompositeEngineContext context;
  private final ByteBuddy buddy;
  private final BuilderVisitorSet builderVisitors;
  private final ClassLoader classLoader;
  private final ClassLoadingStrategy.Default classLoadingStrategy;
  private CompositeMeta compositeMeta;
  private final Set<Class<?>> interfaces;
  private final Set<PropertyMeta> properties;

  private DynamicType.Builder<BaseComposite> builder;

  public ByteBuddyVisitor(ByteBuddy buddy, CompositeEngineContext context) {
    this.context = context;
    this.buddy = buddy;
    this.builderVisitors = context.getBuilderVisitors();
    this.interfaces = new HashSet<>();
    this.properties = new HashSet<>();
    this.classLoader = context.getClassLoader() == null ? getClass().getClassLoader() : context.getClassLoader();
    this.classLoadingStrategy = context.getClassLoader() == null ?
        ClassLoadingStrategy.Default.WRAPPER : ClassLoadingStrategy.Default.INJECTION;
  }

  @Override
  public void visit(CompositeMeta element) {
    this.compositeMeta = element;

    defineType(element);
    defineSerialVersion(element);
    implementComposable(element);
    implementInterfaces(element.getSource());

    element.getProperties().forEach(it -> it.accept(this));
    implementAccessors();

    element.getMixins().forEach(it -> it.accept(this));
    element.getExtensions().forEach(it -> it.accept(this));
    element.getBehaviors().forEach(it -> it.accept(this));

    bindDelegates(element.getDelegateIndex());

    builder = builderVisitors.visit(context, builder, element);

    Class<? extends BaseComposite> enhanced = loadType();
    element.setType(enhanced);
    builderVisitors.visit(context, element);

    log.debug("Enhanced class {}", enhanced.getCanonicalName());
    log.debug("Enhanced with {}", (Object) enhanced.getInterfaces());
    log.debug("Enhanced properties {}", Arrays.toString(enhanced.getDeclaredFields()));
  }

  private void defineSerialVersion(CompositeMeta element) {
    if (contains(element.source.getInterfaces(), Serializable.class)) {
      long version = element.getClassVer() == null ? DEFAULT_SERIAL_VER : element.getClassVer();

      builder = builder.defineField(
          SERIAL_VERSION_UID,
          long.class,
          Ownership.STATIC,
          Visibility.PUBLIC,
          FieldManifestation.FINAL
      ).value(version);
    }
  }

  private void implementComposable(CompositeMeta element) {
    if (contains(element.source.getInterfaces(), Composable.class)) {
      return;
    }

    builder = builder.implement(Composable.class);
    new MixinMeta(Composable.class).getProperties().forEach(it -> it.accept(this));
  }

  private boolean contains(Class<?>[] classes, Class<?> clazz) {
    return Arrays
        .asList(classes)
        .contains(clazz);
  }

  private Class<? extends BaseComposite> loadType() {
    return builder.make()
        .load(this.classLoader, classLoadingStrategy)
        .getLoaded();
  }

  private void defineType(CompositeMeta element) {
    Annotation[] declaredAnnotations = element.getSource().getDeclaredAnnotations();
    Annotation[] decoratorAnnotations = annotationsOf(element.getDecorator());
    Set<Annotation> annotations = newHashSet(asList(addAll(declaredAnnotations, decoratorAnnotations)));

    builder = buddy
        .subclass(BaseComposite.class)
        .name(CompositeUtil.nameOf(element.getSource()))
        .annotateType(annotations.toArray(new Annotation[0]));

    if (element.getInitializers() != null && !element.getInitializers().isEmpty()) {
      builder = builder.method(isDeclaredBy(BaseComposite.class)
          .and(named(BaseComposite.INITIALIZER_NAME)
              .and(takesArguments(0))))
          .intercept(FixedValue.value(element.getInitializers()));
      log.debug("added initializers delegation of {}: {}", element.getSource(), element.getInitializers());
    }

    log.debug("defined type {}", element.getSource());
  }

  private void bindDelegates(Map<DelegateMeta, List<DelegateMeta>> delegateIndex) {
    delegateIndex.forEach((delegateMeta, delegateMetas) ->
        builder = builder.method(matcherOf(delegateMeta)).intercept(buildDelegates(delegateMetas)));
  }

  private Implementation buildDelegates(List<DelegateMeta> delegateMetas) {
    Optional<DelegateMeta> delegateOverride = delegateMetas.stream()
        .filter(it -> it.getMode() == BindingMode.OVERRIDE)
        .findFirst();

    if (delegateOverride.isPresent()) {
      return createMethodDelegation(delegateOverride.get());
    } else {
      List<MethodDelegation> methodDelegations = delegateMetas.stream()
          .sorted(Comparator.reverseOrder())
          .map(this::createMethodDelegation)
          .collect(toList());

      Implementation acc = null;
      for (MethodDelegation delegation : methodDelegations) {
        if (acc == null) {
          acc = delegation;
        } else {
          acc = delegation.andThen(acc);
        }
      }

      return SuperMethodCall.INSTANCE.andThen(acc);
    }
  }

  private MethodDelegation createMethodDelegation(DelegateMeta delegateMeta) {
    return withDefaultConfiguration()
        .withBinders(parameterBinders())
        .to(delegateMeta.getTarget().getDeclaringClass());
  }

  @Override
  public void visit(ExtensionMeta element) {
    implementInterfaces(element.getSource());

    element.getProperties().forEach(it -> it.accept(this));
    implementAccessors();

    builder = builderVisitors.visit(context, builder, element);
  }

  private void implementInterfaces(Class<?>... interfaceTypes) {
    for (Class<?> interfaceType : interfaceTypes) {
      if (interfaces.contains(interfaceType)) {
        continue;
      }
      builder = builder.implement(interfaceType);
      interfaces.add(interfaceType);
    }
  }

  private void implementAccessors() {
    builder = builder
        .method(not(isDefaultMethod()).and((isGetter().or(isSetter()))))
        .intercept(FieldAccessor.ofBeanProperty());

    log.debug("defined accessor {}");
  }

  @Override
  public void visit(MixinMeta element) {
    builder = builderVisitors.visit(context, builder, element);
  }

  @Override
  public void visit(PropertyMeta element) {
    defineProperty(element);

    builder = builderVisitors.visit(context, builder, element);
  }

  @Override
  public void visit(BehaviorMeta element) {
    builder = builderVisitors.visit(context, builder, element);
  }

  private void defineProperty(PropertyMeta element) {
    if (properties.contains(element)) {
      return;
    }

    Class<?> rawType = TypeUtils.getRawType(element.getType(), compositeMeta.source);
    rawType = rawType == null ? Object.class : rawType;

    List<ModifierContributor.ForField> modifiers = new ArrayList<>();
    modifiers.add(Visibility.PRIVATE);
    modifiers.addAll(detectModifiers(element.getAnnotations()));

    builder = builder.defineField(element.getName(), rawType, modifiers)
        .annotateField(element.getAnnotations());
    properties.add(element);


    log.debug("defined property {}", element);
  }

  private Collection<? extends ModifierContributor.ForField> detectModifiers(List<Annotation> annotations) {
    Optional<Annotation> modifiers = annotations.stream()
        .filter(annotation -> annotation.annotationType().equals(Modifier.Modifiers.class))
        .findFirst();

    if (!modifiers.isPresent()) {
      return Collections.emptyList();
    }

    return Arrays.stream(((Modifier.Modifiers) modifiers.get()).value())
        .map(modifier -> {
          switch (modifier.value()) {
            case Modifier.VOLATILE:
              return FieldManifestation.VOLATILE;
            case Modifier.FINAL:
              return FieldManifestation.FINAL;
            case Modifier.TRANSIENT:
              return FieldPersistence.TRANSIENT;
            default:
              return FieldPersistence.PLAIN;
          }
        }).collect(Collectors.toList());
  }
}
