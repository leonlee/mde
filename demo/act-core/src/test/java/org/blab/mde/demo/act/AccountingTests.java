package org.blab.mde.demo.act;

import org.blab.mde.demo.act.controller.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.blab.mde.core.spring.SpringHolder.getBean;
import static org.assertj.core.api.BDDAssertions.then;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AccountingServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = {"management.port=0"})
public class AccountingTests {
    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

//    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                String.format("http://localhost:%d/accounts", this.port), Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().get("size")).isEqualTo(2);

    }

//    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                String.format("http://localhost:%d/health", this.mgt), Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getBean(AccountController.class)).isEqualTo(getBean(AccountController.class));
    }
}
