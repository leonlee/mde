package org.blab.mde.ee.dal.ftl.utils;


public class MiscUtil {

  public static String trimOneStartingNewline(String s) {
    if ( s.startsWith("\r\n") ) s = s.substring(2);
    else if ( s.startsWith("\n") ) s = s.substring(1);
    return s;
  }

  public static String trimOneTrailingNewline(String s) {
    if ( s.endsWith("\r\n") ) s = s.substring(0, s.length()-2);
    else if ( s.endsWith("\n") ) s = s.substring(0, s.length()-1);
    return s;
  }

  public static String trimAndRemoveAngleBrackets (String text) {
    return text
        .replaceAll("<","")
        .replaceAll(">","")
        .trim();
  }


  public static String strip(String s, int n) {
    return s.substring(n, s.length()-n);
  }


  public static String replaceEscapedRightAngle(String s) {
    StringBuilder buf = new StringBuilder();
    int i = 0;
    while ( i<s.length() ) {
      char c = s.charAt(i);
      if ( c=='<' && s.substring(i).startsWith("<\\\\>") ) {
        buf.append("<\\\\>");
        i += "<\\\\>".length();
        continue;
      }
      if ( c=='>' && s.substring(i).startsWith(">\\>") ) {
        buf.append(">>");
        i += ">\\>".length();
        continue;
      }
      if ( c=='\\' && s.substring(i).startsWith("\\>>") &&
          !s.substring(i).startsWith("\\>>>") )
      {
        buf.append(">>");
        i += "\\>>".length();
        continue;
      }
      buf.append(c);
      i++;
    }
    return buf.toString();
  }

}
