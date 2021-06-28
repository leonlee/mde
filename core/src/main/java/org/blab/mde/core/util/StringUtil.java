package org.blab.mde.core.util;


public class StringUtil {
  public static String decapitalize(String name) {
    if (name != null && name.length() != 0) {
      if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
        return name;
      } else {
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
      }
    } else {
      return name;
    }
  }

  public static String capitalize(String name) {
    if (name != null && name.length() != 0) {
      char[] chars = name.toCharArray();
      chars[0] = Character.toUpperCase(chars[0]);
      return new String(chars);
    } else {
      return name;
    }
  }
}
