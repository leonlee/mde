package org.blab.mde.ee.dal.ftl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.blab.mde.core.util.Guarder;


public class FileUtil {

  public static String read(ClassLoader classLoader,String path) throws IOException {

    Guarder.requireNotNull(classLoader);
    Guarder.requireNotNull(path);

    InputStream inputStream = classLoader.getResourceAsStream(path);
    Guarder.requireNotNull(inputStream,"没有找到文件{}",path);

    return streamToString(inputStream);
  }

  private static String streamToString(InputStream inputStream) throws IOException {

    char[] buffer = new char[1024];
    StringBuilder sb = new StringBuilder();
    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

    int p;
    while((p = reader.read(buffer,0,buffer.length)) >=0) {
      sb.append(buffer,0,p);
    }

    return sb.toString();
  }
}
