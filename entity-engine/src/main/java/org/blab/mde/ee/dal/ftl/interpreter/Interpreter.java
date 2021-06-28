package org.blab.mde.ee.dal.ftl.interpreter;

import freemarker.template.utility.StringUtil;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.util.Guarder;
import org.blab.mde.ee.dal.ftl.DSLErrorListener;
import org.blab.mde.ee.dal.ftl.FT;
import org.blab.mde.ee.dal.ftl.FTAgg;
import org.blab.mde.ee.dal.ftl.g4.FTLexer;
import org.blab.mde.ee.dal.ftl.g4.FTParser;
import org.blab.mde.ee.dal.ftl.utils.MiscUtil;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;


@Slf4j
public class Interpreter {

  public static FTAgg interpret(FT ft) {
    FTAgg ftAgg;
    DSLErrorListener dslErrorListener = new DSLErrorListener();
    try {
      CharStream cs = CharStreams.fromString(ft.getTemplate());
      FTLexer lexer = new FTLexer(cs);
      CommonTokenStream tokens = new CommonTokenStream(lexer);

      FTParser parser = new FTParser(tokens);

      lexer.removeErrorListeners();
      parser.removeErrorListeners();

      parser.addErrorListener(dslErrorListener);

      ftAgg = new FTAgg(ft);
      parser.interpreter(ftAgg);

      parseSubFT(tokens, ftAgg);

      Guarder.requireNotBlank(ftAgg.getSqlFT(),"sql模板不能为空");
    } catch (Exception e) {
      log.error("Interpreter ft error", e);
      throw new CrashedException("Interpreter ft error", e);
    }

    return ftAgg;
  }

  public static void parseSubFT(CommonTokenStream tokens, FTAgg ftAgg) {

    tokens.getTokens()
        .stream()
        .filter(t -> t.getType() == FTLexer.SUB_FT)
        .forEach(t -> {
          String tn = MiscUtil.trimAndRemoveAngleBrackets(t.getText());
          FT childFT = ftAgg.childFT(tn);
          exec(ftAgg, childFT, t);
        });
  }

  private static void exec(FTAgg ftAgg, FT childFT, Token token) {

    ftAgg.setSqlFT(StringUtil.replace(ftAgg.getSqlFT(), token.getText(), childFT.getTemplate()));

  }
}
