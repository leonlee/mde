package org.blab.mde.ee;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import static org.junit.Assert.assertEquals;
import static org.stringtemplate.v4.misc.Misc.newline;

public class TestST {
  @Test
  public void testWhere() {
    ST st =
        new ST("<items:{it|<it.left> = <it.right> and }>1 = 1");
    // also testing wacky spaces in aggregate spec
    st.add("items", new ImmutablePair<String, Object>("id", 99));
    st.add("items", new ImmutablePair<String, Object>("name", "Burns"));
    String expecting = "id = 99 and name = Burns and 1 = 1";
    assertEquals(expecting, st.render());
  }

  @Test
  public void TestGroup() {
    String templates =
        "group Java;" + newline +
            "" + newline +
            "where(conditions) ::= <<\n" +
            "<if(conditions)> where " +
            "<condition(first(conditions))> " +
            "<if(rest(conditions))>" +
            "<rest(conditions):{it|<and(it)>}>" +
            "</endif></endif>" + newline +
            ">>" + newline +
            "condition(pair) ::= \"<pair.left> = <pair.right>\"" + newline +
            "and(pair) ::= \"and <pair.left> = <pair.right> \"";

    STGroup group = new STGroupString(templates);
    ST f = group.getInstanceOf("where");

    f.add("conditions", new ImmutablePair<String, Object>("id", 1));
    f.add("conditions", new ImmutablePair<String, Object>("name", "leon"));
    f.add("conditions", new ImmutablePair<String, Object>("age", 23));

    String expecting = " where id = 1 and name = leon and age = 23 ";
    assertEquals(expecting, f.render());
  }

  @Test
  public void TestGroupMap() {
    String templates =
        "group Java;" + newline +
            "" + newline +
            "where(conditions) ::= <<\n" +
            "<if(conditions)> where " +
            "<condition(first(conditions))> " +
            "<if(rest(conditions))>" +
            "<rest(conditions):{it|<and(it)>}>" +
            "</endif></endif>" + newline +
            ">>" + newline +
            "condition(pair) ::= \"<pair> = :<pair>\"" + newline +
            "and(pair) ::= \"and <pair> = :<pair> \"";

    STGroup group = new STGroupString(templates);
    ST f = group.getInstanceOf("where");

    f.add("conditions", ImmutableMap.of("id", 1, "name", "leon", "age", 23));

    String expecting = " where id = :id and name = :name and age = :age ";
    assertEquals(expecting, f.render());
  }
}
