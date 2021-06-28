package org.blab.mde.demo.act.dal;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

import java.util.List;

import org.blab.mde.demo.act.model.Ledger;
import org.blab.mde.ee.annotation.Repository;
import org.blab.mde.ee.dal.RepositoryMixin;
import org.blab.mde.ee.spring.SpringJdbiMixin;


@Repository
public interface LedgerRepository extends SpringJdbiMixin<LedgerRepository.SqlObject>, RepositoryMixin<Ledger, Long, Object> {
  default List<Ledger> findAll() {
    return withDao(SqlObject::findAll);
  }

  @UseStringTemplateSqlLocator
  interface SqlObject {
    @SqlQuery
    List<Ledger> findAll();
  }
}
