package org.blab.mde.ee.dal.jmustache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.ExecutionException;

import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.util.FileUtil;
import org.blab.mde.ee.EntityEngineHolder;

import static org.blab.mde.core.util.FileUtil.isRelative;
import static org.blab.mde.core.util.Guarder.requireNotBlank;

public class MustacheSqlLocator {
  public static final String ENTITY_MUSTACHE_CACHE_SIZE = "entity.mustache.cache-size";
  public static final String MUSTACHE_EXT = ".mustache";
  public static final String CLASSPATH_PREFIX = "classpath:";
  public static final char PARTIAL_SEPARATOR = ':';
  public static final char PATH_SEPARATOR = '/';
  public static final char NAME_SEPARATOR = '.';

  private static LoadingCache<ImmutablePair<Class<?>, String>, Template> cache;
  private static FileLoader fileLoader = new DefaultFileLoader();

  public MustacheSqlLocator() {
    initCache();
  }

  public Template findTemplate(Class<?> type, String templateName) {
    try {
      return cache.get(ImmutablePair.of(type, templateName));
    } catch (ExecutionException e) {
      throw new CrashedException(e);
    }
  }

  private static synchronized void initCache() {
    if (cache != null) {
      return;
    }

    Integer maxSize = (Integer) EntityEngineHolder.getEngine()
        .getCompositeEngine()
        .getProperties()
        .getOrDefault(ENTITY_MUSTACHE_CACHE_SIZE, 2000);

    cache = CacheBuilder.newBuilder()
        .maximumSize(maxSize)
        .build(new CacheLoader<ImmutablePair<Class<?>, String>, Template>() {
          @Override
          public Template load(ImmutablePair<Class<?>, String> pairs) {
            return loadTemplate(pairs.left);
          }
        });
  }

  private static Template loadTemplate(Class<?> type) {
    String basePath = CLASSPATH_PREFIX + type.getPackage().getName().replace(NAME_SEPARATOR, PATH_SEPARATOR);
    String templatePath = basePath + PATH_SEPARATOR + type.getSimpleName() + MUSTACHE_EXT;

    Reader reader = fileLoader.getReader(templatePath);

    return Mustache.compiler()
        .withLoader(partial -> {
          String partialPath = concatPartialPath(basePath, partial);
          return fileLoader.getReader(partialPath);
        })
        .compile(reader);
  }

  private static String concatPartialPath(String basePath, String partial) {
    requireNotBlank(basePath, "bad base path");
    requireNotBlank(partial, "blank partial name");

    partial = partial.replace(PARTIAL_SEPARATOR, PATH_SEPARATOR);

    if (isRelative(partial)) {
      return basePath + PATH_SEPARATOR + partial + MUSTACHE_EXT;
    } else {
      return CLASSPATH_PREFIX
          + partial.replaceFirst(PATH_SEPARATOR + "", "")
          + MUSTACHE_EXT;
    }
  }

  public interface FileLoader {
    Reader getReader(String path);
  }

  private static class DefaultFileLoader implements FileLoader {
    public Reader getReader(String path) {
      File templateFile = FileUtil.getFile(path);
      FileReader fileReader;
      try {
        fileReader = new FileReader(templateFile);
      } catch (FileNotFoundException e) {
        throw new CrashedException(e);
      }
      return new BufferedReader(fileReader);
    }
  }

  public static void setFileLoader(FileLoader loader) {
    fileLoader = loader;
  }
}
