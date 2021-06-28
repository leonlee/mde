package org.blab.mde.ee.dal.ftl;

import org.blab.mde.ee.dal.ftl.interpreter.Interpreter;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.Token;


@Builder
@Data
public class FT {

  private String name;

  private String template;

  private Token nameToken;

  private Token templateToken;

  private FTFile belongToFTFile;

  private FTAgg ftAgg;


  public FTAgg render() {

    if(ftAgg != null) {
      return ftAgg;
    }

    ftAgg = Interpreter.interpret(this);

    return ftAgg;

  }

}
