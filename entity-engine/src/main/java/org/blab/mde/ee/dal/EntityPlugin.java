package org.blab.mde.ee.dal;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.spi.JdbiPlugin;


public class EntityPlugin implements JdbiPlugin {
  @Override
  public void customizeJdbi(Jdbi jdbi) {
    jdbi.registerRowMapper(new EntityMapperFactory());
  }
}
