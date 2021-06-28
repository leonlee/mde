package org.blab.mde.demo.act.dal.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import org.blab.mde.demo.act.model.Contact;
import org.blab.mde.ee.dal.ftl.UseFtlSqlLocator;



//@UseStringTemplateSqlLocator
public interface ContactDao {


  @SqlUpdate
  @GetGeneratedKeys
  @UseFtlSqlLocator
  long insert(@Define("contact") @BindBean Contact contact);

  @SqlUpdate("update contact set name = :name where id = :id")
  int update(@BindBean("contact") Contact contact);

  @SqlQuery
  @UseFtlSqlLocator
  Contact selectById(@Define("id") @Bind("id") long id);
}
