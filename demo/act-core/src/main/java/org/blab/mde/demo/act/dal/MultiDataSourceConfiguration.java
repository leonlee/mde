package org.blab.mde.demo.act.dal;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.blab.mde.ee.dal.EntityPlugin;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@ConditionalOnMissingBean(JdbiConfiguration.class)
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
public class MultiDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("ucDs")
    @ConfigurationProperties("app.datasources.uc")
    public DataSource ucDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("finDs")
    @ConfigurationProperties("app.datasources.fin")
    public DataSource finDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public JdbiFactoryBean defaultJdbi(@Autowired DataSource ds) {
        return new JdbiFactoryBean(ds);
    }

    @Bean("ucJdbi")
    public JdbiFactoryBean ucJdbi(@Autowired @Qualifier("ucDs") DataSource ds) {
        return new JdbiFactoryBean(ds);
    }

    @Bean("finJdbi")
    public JdbiFactoryBean finJdbi(@Autowired @Qualifier("finDs") DataSource ds) {
        return new JdbiFactoryBean(ds);
    }

    @Bean
    public DataSourceHealthIndicator defaultDbIndicator(@Autowired DataSource ds) {
        return new DataSourceHealthIndicator(ds);
    }

    @Bean
    public DataSourceHealthIndicator ucDbIndicator(@Autowired @Qualifier("ucDs") DataSource ds) {
        return new DataSourceHealthIndicator(ds);
    }

    @Bean
    public DataSourceHealthIndicator finDbIndicator(@Autowired @Qualifier("finDs") DataSource ds) {
        return new DataSourceHealthIndicator(ds);
    }

    @Bean
    public JdbiPlugin sqlObjectPlugin() {
        return new SqlObjectPlugin();
    }

    @Bean
    public EntityPlugin entityPlugin() {
        return new EntityPlugin();
    }

    @PostConstruct
    public void initializeDB() {
        Lists.newArrayList(defaultDataSource(), ucDataSource(), finDataSource())
                .forEach(ds -> {
                    Flyway.configure().locations("classpath:db/dml/h2").dataSource(ds)
                            .load()
                            .migrate();
                });
    }
}
