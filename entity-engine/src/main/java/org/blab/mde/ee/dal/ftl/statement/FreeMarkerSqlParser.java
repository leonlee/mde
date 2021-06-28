package org.blab.mde.ee.dal.ftl.statement;

import org.jdbi.v3.core.statement.ColonPrefixSqlParser;
import org.jdbi.v3.core.statement.ParsedSql;
import org.jdbi.v3.core.statement.SqlParser;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;

import org.blab.mde.ee.dal.ftl.FreeMakerParser;


public class FreeMarkerSqlParser extends ColonPrefixSqlParser implements SqlParser {
  public FreeMarkerSqlParser() {
  }

  @Override
  public ParsedSql parse(String sql, StatementContext ctx) {
    try {
      //call freemarker parser before sql parsing
      sql = FreeMakerParser.process(sql, ctx.getAttributes());
      return super.parse(sql, ctx);
    } catch (IllegalArgumentException var4) {
      throw new UnableToCreateStatementException("Exception parser for named parameter replacement", var4, ctx);
    }
  }
}
