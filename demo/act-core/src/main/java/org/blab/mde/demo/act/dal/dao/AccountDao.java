package org.blab.mde.demo.act.dal.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

import org.blab.mde.demo.act.model.Account;
import org.blab.mde.demo.act.model.AccountType;
import org.blab.mde.ee.dal.jmustache.UseMustacheSqlLocator;


//@UseStringTemplateSqlLocator
@UseMustacheSqlLocator
public interface AccountDao {
  @SqlUpdate
  @GetGeneratedKeys
  long insert(@BindBean Account account);

  @SqlBatch
  @GetGeneratedKeys
  long[] insertAll(@BindBean List<Account> accounts);

  @SqlQuery
  Account find(@Bind long id);

  @SqlQuery
  Account findBy(@Define @Bind Long id, @Define @Bind String name);

  @SqlQuery
  List<Account> findAll(@Bind AccountType type, @Bind Long parentId, @Bind int offset, @Bind int pageSize);

  @SqlUpdate
  boolean delete(@Bind long id);

  @SqlUpdate
  boolean deleteByName(@Bind String name);

  @SqlUpdate
  int deleteAll(@BindList List<Long> ids);

  @SqlUpdate
  int deleteAllBy(@Define @Bind AccountType type, @Define @Bind Long parentId, @Bind int pageSize);

}
