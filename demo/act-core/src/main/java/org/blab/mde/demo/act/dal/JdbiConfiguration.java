package org.blab.mde.demo.act.dal;

import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.ee.dal.EntityPlugin;
import org.blab.mde.ee.dal.jmustache.MustacheSqlLocator;

@Configuration
public class JdbiConfiguration {

  @Autowired
  private DataSource ds;
  @Autowired
  private SpringFileLoader springFileLoader;

  @Bean
  @Primary
  public JdbiFactoryBean jdbiFactoryBean() {
    return new JdbiFactoryBean(ds);
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
  public void preset() {
    MustacheSqlLocator.setFileLoader(springFileLoader);
  }

  @Component
  public static class SpringFileLoader implements MustacheSqlLocator.FileLoader {
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public Reader getReader(String path) {
      Resource resource = resourceLoader.getResource(path);
      try {
        return new BufferedReader(new FileReader(resource.getFile()));
      } catch (IOException e) {
        throw new CrashedException(e);
      }
    }
  }
}
