package org.blab.mde.mod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

import org.blab.mde.core.CompositeEngine;
import org.blab.mde.core.CompositeEngineContext;
import org.blab.mde.core.CompositeEngineListener;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireFalse;
import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireTrue;


@Slf4j
public class ModuleEngine implements CompositeEngineListener {
  private Map<String, IModule> modules;

  public ModuleEngine() {
    modules = new HashMap<>();
  }

  public Map<String, IModule> getModules() {
    return modules;
  }

  @Override
  public void afterValidate(CompositeEngineContext context, CompositeEngine engine) {
    loadModules(engine);
    validate();
    register(context);
  }

  private void register(CompositeEngineContext context) {
    Set<String> packages = modules.values().stream()
        .map(IModule::packages)
        .flatMap(Arrays::stream)
        .collect(Collectors.toSet());

    context.getBasePackages().addAll(packages);

    log.debug("registered module packages {}", packages);
  }

  private void validate() {
    checkDuplicated();
  }

  private void checkDuplicated() {
    Set<String> nameSet = modules.values().stream()
        .map(IModule::name)
        .collect(Collectors.toSet());

    requireTrue(nameSet.size() == modules.size(),
        "duplicated module in {}", modules);
  }

  private void loadModules(CompositeEngine engine) {
    ClassLoader classLoader = engine.getClassLoader();
    ClassLoader serviceClassLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
    ServiceLoader<IModule> loader = ServiceLoader.load(IModule.class, serviceClassLoader);

    loader.forEach(this::collectModule);

    log.debug("loaded modules {}", modules);
  }

  private void collectModule(IModule module) {
    requireNotBlank(module.name(), "invalid module name");

    requireFalse(modules.containsKey(module.name()), "duplicated module {}", module);

    modules.put(module.name(), module);
  }
}
