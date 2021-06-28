package org.blab.mde.ee.dal.ftl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.util.Guarder;
import org.blab.mde.ee.dal.ftl.g4.FTFileLexer;
import org.blab.mde.ee.dal.ftl.g4.FTFileParser;
import org.blab.mde.ee.dal.ftl.utils.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.stringtemplate.v4.misc.Misc;

import static org.blab.mde.ee.dal.ftl.utils.Cons.FTFile_SUFFIX;


@Data
@Slf4j
public class FTFile {

  private String sourceName;
  private String text;

  public char delimiterStartChar = '<';
  public char delimiterStopChar = '>';

  private List<FTFile> importList = new CopyOnWriteArrayList<>();

  private Map<String, FT> importFTs = new ConcurrentHashMap<>();

  private Map<String, FT> templates = new ConcurrentHashMap<>();

  private boolean loaded;

  public FTFile(String sourceName, String context) {
    this.sourceName = sourceName;
    this.text = context;
  }

  public boolean isDefined(String tempalteName) {
    if(!loaded) {
      load();
    }
    return templates.containsKey(tempalteName);
  }

  public FT find(String templateName) {
    return templates.get(templateName);
  }

  public void defineTemplate(String templateName,
                             Token templateToken,
                             String template,
                             Token nameToken) {
    Guarder.requireNotNull(templateName, "templateName不能为空");
    Guarder.requireNotNull(template, "template不能为空");

    template = Misc.trimOneStartingNewline(template);
    template = Misc.trimOneTrailingNewline(template);

    templates.put(templateName, FT
        .builder()
        .name(templateName)
        .template(template)
        .nameToken(nameToken)
        .templateToken(templateToken)
        .belongToFTFile(this)
        .build());
  }

  public void importTemplates(Token fileNameToken) {
    String fileName = fileNameToken.getText();

    String ftFileName = fileName.substring(0,fileName.lastIndexOf("."));
    String templateName = fileName.substring(fileName.lastIndexOf(".") + 1);

    FTFile ftFile;
    try {

      ftFile = new FTFile(ftFileName,FileUtil.read(this.getClass().getClassLoader(),resourcePathFor(ftFileName)));

    } catch (IOException e) {
      log.error("import FTFile error",e);
      throw new CrashedException("import FTFile error",e);
    }
    ftFile.load();
    FT importFT = ftFile.find(templateName);

    Guarder.requireNotNull(importFT,"导入的模板{}不能为空",templateName);

    importFTs.put(templateName, importFT);
    importList.add(ftFile);
  }

  public void load() {
    if(loaded) {
      return;
    }

    try{
      CharStream cs = CharStreams.fromString(text);
      FTFileLexer lexer = new FTFileLexer(cs);
      CommonTokenStream tokens = new CommonTokenStream(lexer);

      FTFileParser parser = new FTFileParser(tokens);

      parser.group(this,"");

      loaded = true;
    }catch(Exception e) {
      log.error("antlr4解析模板错误",e);
    }

  }

  public FT ft(String templateName) {

    if(templates.containsKey(templateName)) {
      return templates.get(templateName);
    }

    Guarder.requireNotNull(importList,"{}对应的FT不存在",templateName);

    return importList
        .stream()
        .filter(e -> e.ft(templateName) != null)
        .map(e -> e.ft(templateName))
        .findFirst()
        .orElseThrow(() -> new CrashedException("{}对应的FT不存在import",templateName));

  }


  private static String resourcePathFor(String importName) {
    return importName.replace('.', '/') + FTFile_SUFFIX;
  }
}
