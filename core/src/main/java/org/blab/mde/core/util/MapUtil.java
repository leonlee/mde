package org.blab.mde.core.util;

import java.util.HashMap;
import java.util.Map;

import static org.blab.mde.core.util.Guarder.requireTrue;


public interface MapUtil {
  @SuppressWarnings("unchecked")
  static <V> V valueOf(Map<?, ?> map, Object key) {
    return (V) map.get(key);
  }

  static <K, V> Map<K, V> mapOf(Object... pairs) {
    HashMap<K, V> map = new HashMap<>();

    if (pairs == null || pairs.length == 0) {
      return map;
    }

    requireTrue(pairs.length % 2 == 0, "found unmatched pairs");
    for (int i = 0; i < pairs.length; ) {
      //noinspection unchecked
      map.put((K) pairs[i], (V) pairs[i + 1]);
      i += 2;
    }

    return map;
  }
}
