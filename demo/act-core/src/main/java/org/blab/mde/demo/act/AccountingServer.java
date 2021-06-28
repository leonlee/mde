package org.blab.mde.demo.act;

import org.blab.mde.core.util.CompositeUtil;
import org.blab.mde.demo.act.model.Account;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AccountingServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AccountingServer.class)
                .run(args);
    }

    @Component
    public static class HealthChecker implements HealthIndicator {

        @Override
        public Health health() {
            if (CompositeUtil.typeOf(Account.class) == null) {
                return Health.down()
                        .withDetail("error", "Account Composite not found").build();
            }

            return Health.up().build();
        }
    }

    @GetMapping("/hi")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}

