package org.blab.mde.core;

import net.bytebuddy.ByteBuddy;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.blab.mde.core.annotation.AnnotationType;
import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.annotation.ExtensionType;
import org.blab.mde.core.annotation.MixinType;
import org.blab.mde.core.annotation.PropertyType;
import org.blab.mde.core.meta.AnnotationRegistry;
import org.blab.mde.core.meta.BuilderVisitorSet;
import org.blab.mde.core.meta.ByteBuddyVisitor;
import org.blab.mde.core.meta.CompositeMeta;
import org.blab.mde.core.meta.ExtensionMeta;
import org.blab.mde.core.meta.MetaElementBuilderVisitor;
import org.blab.mde.core.meta.MetaRegistry;
import org.blab.mde.core.meta.TypeScanner;
import org.blab.mde.core.mixin.Composable;
import org.blab.mde.core.spi.ConfigurationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireFalse;
import static org.blab.mde.core.util.Guarder.requireNotNull;


@Slf4j
public class CompositeEngine {
  private boolean sealed;

  @Getter
  private CompositeEngineContext context;

  @Getter
  private SortedSet<String> basePackages;
  @Getter
  private MetaRegistry metaRegistry;
  @Getter
  private AnnotationRegistry annotationRegistry;
  @Getter
  private BuilderVisitorSet builderVisitors;
  @Getter
  private ClassLoader classLoader;
  @Getter
  private Set<CompositeEngineListener> engineListeners;
  @Getter
  private CompositeFactory compositeFactory;
  @Getter
  CompositeEngineProperties properties;

  private TypeScanner scanner;

  public CompositeEngine(String... basePackages) {
    this.basePackages = new TreeSet<>();
    Collections.addAll(this.basePackages, basePackages);

    metaRegistry = new MetaRegistry();
    compositeFactory = new CompositeFactory.Default(metaRegistry);
    annotationRegistry = new AnnotationRegistry();
    engineListeners = new HashSet<>();
    builderVisitors = new BuilderVisitorSet();
    loadConfiguration();
  }

  private void initContext() {
    context = CompositeEngineContext.builder()
        .basePackages(basePackages)
        .properties(properties)
        .classLoader(classLoader)
        .metaRegistry(metaRegistry)
        .compositeFactory(compositeFactory)
        .annotationRegistry(annotationRegistry)
        .engineListeners(engineListeners)
        .builderVisitors(builderVisitors)
        .build();
  }

  private void loadConfiguration() {
    ClassLoader serviceClassLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
    ServiceLoader<ConfigurationService> loader = ServiceLoader.load(ConfigurationService.class, serviceClassLoader);
    SortedSet<ConfigurationService> services = new TreeSet<>();
    loader.forEach(services::add);

    log.debug("loaded configuration service providers {}", services);

    services.forEach(it -> {
      this.basePackages.addAll(it.getPackages());
      this.engineListeners.addAll(it.getListeners());
      this.builderVisitors.addAll(it.getBuilderVisitors());

      if (it.getClassLoader() != null) {
        this.classLoader = it.getClassLoader();
      }
    });
  }

  public CompositeEngine withBuilderVisitors(MetaElementBuilderVisitor... visitors) {
    if (visitors != null && visitors.length > 0) {
      Collections.addAll(this.builderVisitors, visitors);
    }
    return this;
  }

  public CompositeEngine withClassLoader(ClassLoader classLoader) {
    if (classLoader != null) {
      this.classLoader = classLoader;
    }
    return this;
  }

  public CompositeEngine withEngineListeners(CompositeEngineListener... listeners) {
    if (listeners != null && listeners.length > 0) {
      Collections.addAll(this.engineListeners, listeners);
    }
    return this;
  }

  public CompositeEngine withCompositeFactory(CompositeFactory factory) {
    requireNotNull(factory);
    this.compositeFactory = factory;
    return this;
  }

  public CompositeEngine withProperties(CompositeEngineProperties properties) {
    requireNotNull(properties);
    this.properties = properties;
    return this;
  }

  public CompositeEngine start() {
    if (sealed) {
      log.warn("composite engine was sealed with {}", basePackages);
      return this;
    }
    log.debug("composites is starting... {}", basePackages);

    initContext();
    validate();
    initScanner();
    registerAnnotations();
    loadMeta();
    buildComposites();
    seal();
    engineListeners.forEach(it -> it.afterStart(context, this));

    log.debug("composites was started.");

    return this;
  }

  private void validate() {
    requireNotNull(basePackages, "basePackage is null");
    requireFalse(sealed, "The composite engine was already sealed");
    engineListeners.forEach(it -> it.afterValidate(context, this));
  }

  private void registerAnnotations() {
    annotationRegistry.register(AnnotationType.COMPOSITE, scanner.scanAnnotations(CompositeType.class));
    annotationRegistry.register(AnnotationType.EXTENSION, scanner.scanAnnotations(ExtensionType.class));
    annotationRegistry.register(AnnotationType.PROPERTY, scanner.scanAnnotations(PropertyType.class));
    annotationRegistry.register(AnnotationType.MIXIN, scanner.scanAnnotations(MixinType.class));
  }

  private void loadMeta() {
    SortedSet<Class<?>> composites = new TreeSet<>(Comparator.comparing(Class::getCanonicalName));
    SortedSet<Class<?>> extensions = new TreeSet<>(ExtensionMeta::compareExtensionClass);
    SortedSet<Class<?>> mixins = new TreeSet<>(Comparator.comparing(Class::getCanonicalName));

    composites.addAll(scanner.scanTypes(annotationRegistry.annotationsOf(AnnotationType.COMPOSITE)));
    extensions.addAll(scanner.scanTypes(annotationRegistry.annotationsOf(AnnotationType.EXTENSION)));
    mixins.addAll(scanner.scanTypes(annotationRegistry.annotationsOf(AnnotationType.MIXIN)));
    mixins.add(Composable.class);

    log.debug("scanned composite: {}", composites);
    log.debug("scanned extensions: {}", extensions);
    log.debug("scanned mixins: {}", mixins);

    this.metaRegistry.populate(composites, extensions, mixins);
  }

  private void initScanner() {
    scanner = new TypeScanner(this.basePackages);
  }

  private void buildComposites() {
    ByteBuddy buddy = new ByteBuddy();
    metaRegistry.getMapping().forEach(
        (__, compositeMeta) -> compositeMeta.accept(new ByteBuddyVisitor(buddy, context))
    );

    log.debug("loaded mapping:");
    metaRegistry.getMapping().forEach((type, meta) -> log.debug("type: {}\n{}", type, meta));
  }

  private void seal() {
    sealed = true;
  }

  public Collection<CompositeMeta> getComposites() {
    return metaRegistry.getMapping().values();
  }

  public <T> T create(Class<?> type) {
    T instance = compositeFactory.newComposite(type);
    engineListeners.forEach(it -> it.afterNewComposite(context, instance));

    return instance;
  }

  public <T> T createOf(Class<T> type) {
    T instance = compositeFactory.newComposite(type);
    engineListeners.forEach(it -> it.afterNewComposite(context, instance));

    return instance;
  }

  public <T> Class<T> typeOf(Class<T> originType) {
    return metaRegistry.getCompositeType(originType);
  }

  public <T> Class<T> typeOf(String name) {
    return metaRegistry.getCompositeType(name);
  }

  public <T> Class<T> sourceOf(String name) {
    return metaRegistry.getCompositeSource(name);
  }

  public String nameOf(Class<?> originType) {
    return metaRegistry.getCompositeName(originType);
  }

  public CompositeMeta metaOf(Class<?> originType) {
    return metaRegistry.find(originType);
  }

  public CompositeMeta metaOf(String name) {
    return metaOf(sourceOf(name));
  }
}
