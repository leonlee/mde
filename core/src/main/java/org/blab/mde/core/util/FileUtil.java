package org.blab.mde.core.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.blab.mde.core.util.Guarder.requireTrue;


public interface FileUtil {
  static File getFile(String resourcePath) {
    requireNotBlank(resourcePath, "Resource location must not be null");

    if (resourcePath.startsWith("classpath:")) {
      String path = resourcePath.substring("classpath:".length());
      String description = "class path resource [" + path + "]";
      ClassLoader cl = ClassUtil.getDefaultClassLoader();

      URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
      requireNotNull(url, "{} cannot be resolved", description);

      return getFile(url, description);
    } else {
      try {
        return getFile(new URL(resourcePath));
      } catch (MalformedURLException e) {
        return new File(resourcePath);
      }
    }
  }

  static File getFile(URL resourceUrl) {
    return getFile(resourceUrl, "URL");
  }

  static File getFile(URL resourceUrl, String description) {
    requireNotNull(resourceUrl, "Resource URL must not be null");
    requireTrue("file".equals(resourceUrl.getProtocol()), "{} cannot be resolved", description);

    try {
      return new File(toURI(resourceUrl).getSchemeSpecificPart());
    } catch (URISyntaxException e) {
      return new File(resourceUrl.getFile());
    }
  }

  static URI toURI(URL url) throws URISyntaxException {
    return toURI(url.toString());
  }

  static URI toURI(String location) throws URISyntaxException {
    return new URI(StringUtils.replace(location, " ", "%20"));
  }


  static boolean isRelative(String path) {
    return !isAbsolute(path);
  }

  static boolean isAbsolute(String path) {
    return path.length() != 0 && path.startsWith("/");
  }
}
