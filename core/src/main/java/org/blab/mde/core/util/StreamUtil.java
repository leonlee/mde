package org.blab.mde.core.util;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collector;


public class StreamUtil {
  public static <T> Collector<T, TreeSet<T>, TreeSet<T>> toTreeSet(Comparator<T> comparator) {
    return Collector.of(
        () -> new TreeSet<>(comparator),
        TreeSet::add,
        (left, right) -> {
          left.addAll(right);
          return left;
        });
  }

  public static <T> Collector<T, TreeSet<T>, TreeSet<T>> toTreeSet() {
    return Collector.of(
        TreeSet::new,
        TreeSet::add,
        (left, right) -> {
          left.addAll(right);
          return left;
        });
  }
}
