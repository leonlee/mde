package org.blab.mde.demo.act.model;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

import org.blab.mde.core.mixin.Printable;
import org.blab.mde.demo.act.model.mixin.UserMixin;
import org.blab.mde.ee.annotation.Aggregate;
import org.blab.mde.ee.annotation.Field;
import org.blab.mde.ee.mixin.Auditable;
import org.blab.mde.ee.mixin.WithLongID;
import org.blab.mde.ee.util.EntityUtil;


@Aggregate
public interface Ledger extends WithLongID, Auditable, UserMixin, Printable {

  static Ledger make() {
    return EntityUtil.createOf(Ledger.class);
  }

  static List<Ledger> findAll() {
    return EntityUtil.singleton(Ledger.class).query();
  }

  static List<Transaction> findTransactions() {
    return EntityUtil.singleton(Ledger.class).queryTransactions();
  }

  @Field
  long getTxId();

  void setTxId(long txId);

  @Field
  long getAccountId();

  void setAccountId(long accountId);


  @Field
  long getDebit();

  void setDebit(long debit);

  @Field
  long getCredit();

  void setCredit(long credit);

  @Field
  long getBalance();

  void setBalance(long balance);

  @Field
  boolean isCleared();

  void setCleared(boolean cleared);

  @Field
  Date getClearedOn();

  void setClearedOn(Date clearedOn);

  default Transaction saveTransaction(Transaction transaction) {
    return null;
  }

  default List<Ledger> query() {
    return Lists.newArrayList();
  }

  default List<Transaction> queryTransactions() {
    return Lists.newArrayList();
  }
}
