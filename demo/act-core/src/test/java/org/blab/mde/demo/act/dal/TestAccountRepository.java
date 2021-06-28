package org.blab.mde.demo.act.dal;

import org.blab.mde.demo.act.model.Account;
import org.blab.mde.demo.act.model.AccountType;
import org.blab.mde.demo.act.model.manager.AccountManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class TestAccountRepository extends BaseRepositoryTest {
  //    @Test
  public void saveAccountTest() {
    Account account = engine.createOf(Account.class);
    account.setUid(1);
    account.setCode(10001);
    account.setName("cash");
    account.setType(AccountType.ASSET);
    account.setBalance(0);
    account.setCreatedBy(1);
    account.setUpdatedBy(1);

    account = account.save();

    AccountManager manager = engine.createOf(AccountManager.class);
    Account cashAct = manager.load(account.getId());

    assertNotNull(cashAct);
    assertEquals(1, cashAct.getUid());
    assertEquals(10001, cashAct.getCode());
    assertEquals("cash", cashAct.getName());
    assertEquals(AccountType.ASSET, cashAct.getType());
    assertEquals(0, cashAct.getBalance());
    assertEquals(1, cashAct.getCreatedBy());
    assertEquals(1, cashAct.getUpdatedBy());
    assertFalse(cashAct.isDeleted());
  }

}
