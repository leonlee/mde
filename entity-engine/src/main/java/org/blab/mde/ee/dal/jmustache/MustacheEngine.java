package org.blab.mde.ee.dal.jmustache;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.TemplateEngine;

import java.io.StringWriter;


public class MustacheEngine implements TemplateEngine {
  @Override
  public String render(String sql, StatementContext ctx) {
    Template template = Mustache.compiler().compile(sql);
    StringWriter writer = new StringWriter();
    template.execute(ctx.getAttributes(), writer);

    return writer.toString();
  }
}
