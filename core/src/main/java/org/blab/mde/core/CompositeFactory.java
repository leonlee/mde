package org.blab.mde.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.blab.mde.core.annotation.CompositeType;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.meta.BaseComposite;
import org.blab.mde.core.meta.CompositeMeta;
import org.blab.mde.core.meta.MetaRegistry;
import org.blab.mde.core.mixin.Composable;

import static org.blab.mde.core.CompositeFactory.Default.SingletonContext.INSTANCE;


public interface CompositeFactory {
  <T> T newComposite(Class<?> type);

  class Default implements CompositeFactory {
    private MetaRegistry registry;

    public Default(MetaRegistry registry) {
      this.registry = registry;
    }

    @Override
    public <T> T newComposite(Class<?> type) {
      CompositeMeta meta = registry.find(type);
      return createComposite(meta);
    }

    @SuppressWarnings("unchecked")
    private synchronized <T> T createComposite(CompositeMeta meta) {
      Class<? extends BaseComposite> type = meta.getType();
      Composable instance;
      if (Objects.equals(meta.getScope(), CompositeType.SCOPE_SINGLETON)) {
        instance = INSTANCE.instances.computeIfAbsent(type, __ -> newInstance(type));
      } else {
        instance = newInstance(type);
      }

      return (T) instance;
    }

    private Composable newInstance(Class<? extends BaseComposite> type) {
      Composable instance;
      try {
        instance = (Composable) type.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException
          | NoSuchMethodException | InvocationTargetException e) {
        throw new CrashedException(e);
      }

      return instance;
    }


    enum SingletonContext {
      INSTANCE;

      ConcurrentMap<Class<? extends BaseComposite>, Composable> instances;

      SingletonContext() {
        instances = new ConcurrentSkipListMap<>(Comparator.comparing(Class::getCanonicalName));
      }
    }
  }
}
