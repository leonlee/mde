package org.blab.mde.ee.dal;

import java.lang.reflect.Type;
import java.util.Map;

public class EntityJoinRow {
  private final Map<Type, Object> entries;

  EntityJoinRow(Map<Type, Object> entries) {
    this.entries = entries;
  }

  /**
   * Return the value mapped for a given class.
   *
   * @param <T>   the type to map
   * @param klass the type that was mapped
   * @return the value for that type
   */
  public <T> T get(Class<T> klass) {
    return klass.cast(get((Type) klass));
  }

  /**
   * Return the value mapped for a given type.
   *
   * @param type the type that was mapped
   * @return the value for that type
   */
  public Object get(Type type) {
    Object result = entries.get(type);
    if (result == null && !entries.containsKey(type)) {
      throw new IllegalArgumentException("no result stored for " + type);
    }
    return result;
  }
}
