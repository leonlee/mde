package org.blab.mde.demo.act.controller;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.blab.mde.core.annotation.Composite;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.spring.ControllerDecorator;
import org.blab.mde.core.util.CompositeUtil;
import org.blab.mde.demo.act.model.Account;
import org.blab.mde.demo.act.model.AccountType;
import org.blab.mde.demo.act.model.manager.AccountManager;
import org.blab.mde.demo.act.service.AccountService;
import org.blab.mde.ee.dal.DefaultQuery;
import org.blab.mde.ee.dal.PageList;

import static org.apache.commons.lang3.StringUtils.upperCase;


//@Slf4jj
//@Controller
@RequestMapping("/accounts")
@Composite(decorator = ControllerDecorator.class)
public interface AccountController {
  Logger log = LoggerFactory.getLogger(AccountController.class);

  @Autowired
  @Property
  AccountService getAccountService();

  void setAccountService(AccountService accountService);

  @Autowired
  @Property
  AccountManager getActManager();

  void setActManager(AccountManager actManager);

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  @ResponseBody
  default Account create(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) Long code) {

    Account account = getActManager().create();

    account.setName(name);
    account.setType(AccountType.valueOf(upperCase(type)));
    account.setCode(code);
    account.setUid(1);

    account.setCreatedBy(1);
    account.setUpdatedBy(1);

    LocalDateTime now = LocalDateTime.now();
    account.setCreatedOn(now);
    account.setUpdatedOn(now);

    account = account.save();

    return account;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  default Map home() {
    Account cashAccount = getActManager().create();

    getAccountService().ping();

    cashAccount.setName("cash");
    cashAccount.setType(AccountType.ASSET);
    cashAccount.setBalance(100L);
    cashAccount.setCode(1000001L);
    cashAccount.setCreatedBy(1);
    cashAccount.setUpdatedBy(1);
    cashAccount.setCreatedOn(LocalDateTime.now());
    cashAccount.setUpdatedOn(LocalDateTime.now());
    cashAccount.save();

    Account checkAccount = getActManager().create();
    checkAccount.setName("check");
    checkAccount.setType(AccountType.ASSET);
    checkAccount.setBalance(200L);
    checkAccount.setCode(1000002L);
    checkAccount.setCreatedBy(2);
    checkAccount.setUpdatedBy(2);
    checkAccount.setCreatedOn(LocalDateTime.now());
    checkAccount.setUpdatedOn(LocalDateTime.now());
    checkAccount.save();

    log.debug("======= cashAccount {}", cashAccount.toString());
    log.debug("======= checkAccount {}", checkAccount.toString());

    DefaultQuery query = CompositeUtil.create(DefaultQuery.class);
    PageList<Account> all = getActManager().loadAll(query);

    all.forEach(act -> log.debug("got account: {}", act));

    long deleted = getActManager().removeAll(Lists.newArrayList(cashAccount.getId(), checkAccount.getId()));
    log.debug("deleted accounts: {}", deleted);


    return ArrayUtils.toMap(new Object[]{new HashMap.SimpleImmutableEntry<>("size", all.size())});
  }
}
