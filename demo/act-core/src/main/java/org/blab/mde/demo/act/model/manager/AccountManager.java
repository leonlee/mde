package org.blab.mde.demo.act.model.manager;

import com.google.common.collect.ImmutableMap;

import org.blab.mde.demo.act.dal.AccountRepository;
import org.blab.mde.demo.act.model.Account;
import org.blab.mde.ee.annotation.Manager;
import org.blab.mde.ee.dal.ManagerMixin;


@Manager
public interface AccountManager extends
    ManagerMixin<
        Account,
        Long,
        ImmutableMap<String, ?>,
        AccountRepository> {
}
