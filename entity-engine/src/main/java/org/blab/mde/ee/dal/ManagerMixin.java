package org.blab.mde.ee.dal;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.util.List;

import javax.inject.Inject;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.util.ClassUtil;
import org.blab.mde.ee.util.EntityUtil;

import static org.blab.mde.core.util.Guarder.unsupportedOperation;

@Mixin
public interface ManagerMixin<Entity, Key, Criteria, Repository extends RepositoryMixin<Entity, Key, Criteria>> {
  @Property
  @Inject
  Repository getRepo();

  void setRepo(Repository repo);

  /**
   * Only for repository type inference, need to be implemented by concrete class.<br/>
   *
   * @return repo type
   */
  Repository repoType();

  /**
   * Only for entity type inference, need to be implemented by concrete class.<br/>
   *
   * @return repo type
   */
  default Entity _entityType() {
    return unsupportedOperation();
  }

  default Entity create() {
    Class<?> type;
    type = TypeUtils.getRawType(ClassUtil.getMethodType(getClass(), "_entityType"), this.getClass());
    return EntityUtil.create(type);
  }

  default PageList<Entity> loadAll(QueryMixin<Criteria> query) {
    return getRepo().loadAll(query);
  }

  default Entity load(Key id) {
    return getRepo().load(id);
  }

  default Entity load(QueryMixin<Criteria> query) {
    return getRepo().load(query);
  }

  default boolean remove(Key id) {
    return getRepo().remove(id);
  }

  default boolean remove(QueryMixin<Criteria> query) {
    return getRepo().remove(query);
  }

  default long removeAll(List<Key> idList) {
    return getRepo().removeAll(idList);
  }

  default long removeAll(QueryMixin<Criteria> query) {
    return getRepo().removeAll(query);
  }

  default long updateAll(List<Entity> entities) {
    return getRepo().updateAll(entities);
  }
}
