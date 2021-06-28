package org.blab.mde.demo.act.dal;

import com.google.common.collect.ImmutableMap;

import java.util.List;

import org.blab.mde.demo.act.dal.dao.AccountDao;
import org.blab.mde.demo.act.model.Account;
import org.blab.mde.demo.act.model.AccountType;
import org.blab.mde.ee.annotation.Repository;
import org.blab.mde.ee.dal.PageList;
import org.blab.mde.ee.dal.QueryMixin;
import org.blab.mde.ee.dal.RepositoryMixin;
import org.blab.mde.ee.spring.SpringJdbiMixin;

import static org.blab.mde.core.util.Guarder.requireFalse;
import static org.blab.mde.core.util.Guarder.requireTrue;
import static org.blab.mde.core.util.MapUtil.valueOf;
import static org.blab.mde.ee.dal.QueryMixin.DEFAULT_PAGE_SIZE;


@Repository
public interface AccountRepository extends SpringJdbiMixin<AccountDao>,
    RepositoryMixin<Account, Long, ImmutableMap<String, ?>> {

  @Override
  default Account load(Long id) {
    return withDao(dao -> dao.find(id));
  }

  @Override
  default Account load(QueryMixin<ImmutableMap<String, ?>> query) {
    ImmutableMap<String, ?> conditions = query.getCriteria();
    final Long id = valueOf(conditions, "id");
    final String name = valueOf(conditions, "name");

    return withDao(dao -> dao.findBy(id, name));
  }

  @Override
  default <V> PageList<V> loadAll(QueryMixin<ImmutableMap<String, ?>> query) {
    ImmutableMap<String, ?> conditions = query.getCriteria();
    final AccountType type = valueOf(conditions, "type");
    final Long parentId = valueOf(conditions, "parentId");

    List<Account> accounts = with(
        AccountDao.class,
        dao -> dao.findAll(type, parentId, query.offset(), query.getPageSize())
    );

    PageList<V> pageList = PageList.create();
    pageList.elements(accounts)
        .page(query.getPage())
        .pageSize(query.getPageSize());

    return pageList;
  }

  @Override
  default boolean remove(Long id) {
    return withDao(dao -> dao.delete(id));
  }

  @Override
  default boolean remove(QueryMixin<ImmutableMap<String, ?>> query) {
    ImmutableMap<String, ?> conditions = query.getCriteria();
    final String name = valueOf(conditions, "name");

    return withDao(dao -> dao.deleteByName(name));
  }

  @Override
  default int removeAll(final List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return 0;
    }

    requireFalse(ids.size() > DEFAULT_PAGE_SIZE, "reach the limit of page size {}", ids.size());

    return withDao(dao -> dao.deleteAll(ids));
  }

  @Override
  default int removeAll(QueryMixin<ImmutableMap<String, ?>> query) {
    ImmutableMap<String, ?> conditions = query.getCriteria();
    final AccountType type = valueOf(conditions, "type");
    final Long parentId = valueOf(conditions, "parentId");

    return withDao(dao -> dao.deleteAllBy(type, parentId, query.getPageSize()));
  }

  @Override
  default boolean removeEntity(Account account) {
    return remove(account.getId());
  }

  @Override
  default Account save(Account account) {
    long id = withDao(dao -> dao.insert(account));
    account.setId(id);

    return account;
  }

  @Override
  default List<Account> saveAll(List<Account> accounts) {
    requireTrue(
        accounts.size() > DEFAULT_PAGE_SIZE,
        "reach the limit of page size {}", accounts.size()
    );

    long[] ids = withDao(dao -> dao.insertAll(accounts));

    for (int i = 0; i < ids.length; i++) {
      accounts.get(i).setId(ids[i]);
    }

    return accounts;
  }

  @Override
  default boolean update(Account account) {
    return false;
  }

  @Override
  default int updateAll(List<Account> accounts) {
    return 0;
  }
}
