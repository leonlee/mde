package org.blab.mde.ee.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;
import java.util.TreeMap;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;

import static org.blab.mde.core.util.Guarder.requireNotBlank;

@Mixin
public interface OrderMixin {
  Logger log = LoggerFactory.getLogger(OrderMixin.class);

  /**
   * Orders map, key: name, value: isAsc. </br>
   *
   * @return orders map
   */
  @Property
  SortedMap<String, Boolean> getOrders();

  /**
   * Orders map, key: name, value: isAsc. </br>
   *
   * @param orders orders map
   */
  void setOrders(SortedMap<String, Boolean> orders);

  @Initializer
  default void initOrderMixn() {
    setOrders(new TreeMap<>());
  }

  default OrderMixin addOrder(String name, boolean isAsc) {
    requireNotBlank(name, "invalid order name");
    if (getOrders().containsKey(name)) {
      log.warn("order {} was overridden, old: {}, new: {}", name, getOrders().get(name), isAsc);
    }

    getOrders().put(name, isAsc);

    return this;
  }

  default OrderMixin removeOrder(String name) {
    requireNotBlank(name, "invalid order name");

    getOrders().remove(name);

    return this;
  }

  default String toOrderBy() {
    if (getOrders().isEmpty()) {
      return "";
    }

    StringBuilder builder = new StringBuilder(" order by ");

    getOrders().forEach((name, isAsc) ->
        builder.append(name)
            .append(" ")
            .append(isAsc ? "asc" : "desc")
            .append(", "));

    String orderBy = builder.deleteCharAt(builder.lastIndexOf(",")).toString();

    log.debug("generated order by clause: {}", orderBy);

    return orderBy;
  }
}
