package org.blab.mde.ee.dal.ftl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.util.Guarder;
import org.blab.mde.ee.dal.ftl.utils.FileUtil;

import static org.blab.mde.ee.dal.ftl.utils.Cons.FTFile_SUFFIX;


public class FreeMarkerTemplateSqlLocator {

  private static final Map<String,FTFile> CACHE = new ConcurrentHashMap<>();

  public static FTFile findFTFile(Class<?> type) {

    Guarder.requireNotNull(type,"查找FTFile中type不能为空");

    return findFTFile(type.getClassLoader(), resourcePathFor(type));
  }

  public static FTFile findFTFile(ClassLoader classLoader,String path) {

    return CACHE.computeIfAbsent(path, p -> readFTFile(classLoader,path));
  }

  private static FTFile readFTFile(ClassLoader classLoader,String path) {

    String context;
    try {
      context = FileUtil.read(classLoader,path);

    } catch (Exception e) {
      throw new CrashedException("读取模板文件错误{}", path);
    }

    FTFile ftFile = new FTFile(path,context);
    return ftFile;
  }


  private static String resourcePathFor(Class<?> clazz) {
    return clazz.getName().replace('.', '/') + FTFile_SUFFIX;
  }


}
