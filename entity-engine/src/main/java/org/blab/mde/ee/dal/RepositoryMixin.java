package org.blab.mde.ee.dal;

import java.util.List;

import org.blab.mde.core.annotation.Mixin;

@Mixin
public interface RepositoryMixin<Entity, Key, Criteria> {
  Entity load(Key id);

  Entity load(QueryMixin<Criteria> query);

  <V> PageList<V> loadAll(QueryMixin<Criteria> query);

  boolean remove(Key id);

  boolean remove(QueryMixin<Criteria> query);

  int removeAll(List<Key> idList);

  int removeAll(QueryMixin<Criteria> query);

  boolean removeEntity(Entity entity);

  Entity save(Entity entity);

  List<Entity> saveAll(List<Entity> entities);

  boolean update(Entity entity);

  int updateAll(List<Entity> entities);
}
