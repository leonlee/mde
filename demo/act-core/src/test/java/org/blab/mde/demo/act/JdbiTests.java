package org.blab.mde.demo.act;

import org.blab.mde.demo.act.dal.dao.AccountDao;
import org.blab.mde.demo.act.model.Account;
import org.blab.mde.demo.act.model.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountingServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = {"management.port=0"})
@Slf4j
public class JdbiTests {
    @Autowired
    private Jdbi jdbi;

    @Test
    public void jdbiTest() {
        assertNotNull(jdbi);
        List<Account> all = jdbi.open().attach(AccountDao.class)
                .findAll(AccountType.ASSET, 1L, 0, 10);

        log.info("============================== got all {}", all.size());

        assertNotNull(all);
    }
}
