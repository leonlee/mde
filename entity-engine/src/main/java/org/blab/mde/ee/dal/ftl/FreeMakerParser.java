package org.blab.mde.ee.dal.ftl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.blab.mde.core.exception.CrashedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class FreeMakerParser {

  private static final String DEFAULT_TEMPLATE_KEY = "default_template_key";

  private static final String DEFAULT_TEMPLATE_EXPRESSION = "default_template_expression";

  private static Map<String, Template> templateMapCache = new HashMap<String, Template>();

  private static Map<String, Template> expressionMapCache = new HashMap<String, Template>();

  private static final Configuration CONFIGURER = new Configuration(Configuration.VERSION_2_3_0);

  public static String process(String expression, Object root) {

    String sql;

    try (StringWriter out = new StringWriter();
         StringReader reader = new StringReader(expression)) {

      Template template = expressionMapCache
          .computeIfAbsent(expression, _s -> createTemplate(expression,reader));

      template.process(root, out);
      sql = out.toString().replaceAll("[\t\r\n\\s]+", StringUtils.SPACE);

    } catch (Exception e) {
      throw new CrashedException("FreeMakerParser.process error. " + e.getMessage(), e);
    }

    return sql;
  }

  private static Template createTemplate(String templateKey, StringReader reader) throws CrashedException {
    Template template;
    try {
      template = new Template(templateKey, reader, CONFIGURER);
    } catch (IOException e) {
      throw new CrashedException("create template error",e);
    }
    template.setNumberFormat("#");
    template.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
    //TODO freemarker自带的缓存验证
    return template;
  }
}
