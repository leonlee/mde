package org.blab.mde.ee.dal;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.util.CompositeUtil;

import static org.blab.mde.core.util.ClassUtil.getMethodType;
import static org.apache.commons.lang3.reflect.TypeUtils.getRawType;

@Mixin
public interface QueryMixin<C> extends AttributesMixin, OrderMixin {
  int DEFAULT_PAGE_SIZE = 100;

  @Property
  int getPageSize();

  void setPageSize(int pageSize);

  @Property
  int getPage();

  void setPage(int page);

  @Property
  C getCriteria();

  void setCriteria(C criteria);


  @Initializer
  default void initQueryMixin() {
    setPage(1);
    setPageSize(DEFAULT_PAGE_SIZE);
    initCriteria();
  }

  default void initCriteria() {
    Class<?> type;
    type = getRawType(getMethodType(getClass(), "getCriteria"), this.getClass());

    try {
      C instance;
      if (CompositeUtil.isComposite(type)) {
        instance = CompositeUtil.create(type);
      } else {
        instance = createCriteria();
        //noinspection unchecked
        instance = instance == null ? (C) type.newInstance() : instance;
      }

      setCriteria(instance);
    } catch (InstantiationException | IllegalAccessException e) {
      setCriteria(createCriteria());
    }
  }

  /**
   * Provide specific constructing of <code>Criteria</code> instance.<br/>
   *
   * @return criteria instance
   */
  default C createCriteria() {
    return null;
  }

  default int offset() {
    return (getPage() - 1) * getPageSize();
  }
}
