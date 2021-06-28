package org.blab.mde.ee;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.blab.mde.ee.dal.ftl.FreeMakerParser;

import static org.blab.mde.core.util.ClassUtil.populate;
import static org.blab.mde.core.util.MapUtil.mapOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestFreeMarkerParsing {

  private static final String SIMPLE_MODEL_TEMPLATE = "insert into user_contact\n"
      + "        (\n"
      + "            <#if firstName ? exists> first_name,</#if>\n"
      + "            <#if lastName ? exists> last_name,</#if>\n"
      + "            <#if phone ? exists> phone,</#if>\n"
      + "            <#if phoneAreacode ? exists> phone_areacode,</#if>\n"
      + "            <#if mobile ? exists> mobile,</#if>\n"
      + "            <#if mobileAreacode ? exists> mobile_areacode,</#if>\n"
      + "            <#if email ? exists> email,</#if>\n"
      + "            <#if qq ? exists> qq,</#if>\n"
      + "            <#if fax ? exists> fax,</#if>\n"
      + "            <#if province ? exists> province,</#if>\n"
      + "            <#if city ? exists> city,</#if>\n"
      + "            <#if address ? exists> address,</#if>\n"
      + "            <#if zipCode ? exists> zip_code,</#if>\n"
      + "            <#if createTime ? exists> create_time,</#if>\n"
      + "            <#if modifyTime ? exists> modify_time,</#if>\n"
      + "            user_id,\n"
      + "            user_name\n"
      + "        )\n"
      + "    values\n"
      + "        (\n"
      + "            <#if firstName ? exists> :firstName,</#if>\n"
      + "            <#if lastName ? exists> :lastName,</#if>\n"
      + "            <#if phone ? exists> :phone,</#if>\n"
      + "            <#if phoneAreacode ? exists> :phoneAreacode,</#if>\n"
      + "            <#if mobile ? exists> :mobile,</#if>\n"
      + "            <#if mobileAreacode ? exists> :mobileAreacode,</#if>\n"
      + "            <#if email ? exists> :email,</#if>\n"
      + "            <#if qq ? exists> :qq,</#if>\n"
      + "            <#if fax ? exists> :fax,</#if>\n"
      + "            <#if province ? exists> :province,</#if>\n"
      + "            <#if city ? exists> :city,</#if>\n"
      + "            <#if address ? exists> :address,</#if>\n"
      + "            <#if zipCode ? exists> :zipCode,</#if>\n"
      + "            <#if createTime ? exists> :createTime,</#if>\n"
      + "            <#if modifyTime ? exists> :modifyTime,</#if>\n"
      + "            :userId,\n"
      + "            :userName\n"
      + "        )";

  private static final String COMPLEX_MODEL_TEMPLATE = "insert into user_contact\n"
      + "        (\n"
      + "            <#if account.firstName ? exists> first_name,</#if>\n"
      + "            <#if account.contactName ? exists> contact_name</#if>\n"
      + "        )\n"
      + "    values\n"
      + "        (\n"
      + "            <#if account.firstName ? exists> :firstName,</#if>\n"
      + "            <#if account.contactName ? exists> :contactName</#if>\n"
      + "        )";

  private static final String SIMPLE_MODEL_SQL = "insert into user_contact ( first_name, user_id, user_name )" +
      " values ( :firstName, :userId, :userName )";

  private static final String COMPLEX_MODEL_SQL = "insert into user_contact ( first_name, contact_name )" +
      " values ( :firstName, :contactName )";

  @Test
  public void testPerformance() {
    long begin = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      parse(SIMPLE_MODEL_TEMPLATE, new HashMap<String, Object>());
    }
    long end = System.currentTimeMillis();

    System.out.printf("elapsed: %dms\n", end - begin);
    assertTrue("ftl parse performance failed", end - begin < 5000);
  }

  @Test
  public void testPlainTemplate() {
    DefaultObjectWrapper defaultObjectWrapper = new DefaultObjectWrapper(Configuration.VERSION_2_3_28);
    Map<String, String> map = mapOf("firstName", "shaoyuqi");
    SimpleHash simpleHash = new SimpleHash(map, defaultObjectWrapper);

    String sql = parse(SIMPLE_MODEL_TEMPLATE, simpleHash);

    assertEquals(SIMPLE_MODEL_SQL, sql);
  }

  @Test
  public void testComplexTemplate() {
    Account account = populate(Account::new, it -> {
      it.setFirstName("shao");
      it.setContactName("shaoyuqi");
    });
    Map<String, Object> context = mapOf("account", account);

    DefaultObjectWrapper defaultObjectWrapper = new DefaultObjectWrapper(Configuration.VERSION_2_3_28);
    SimpleHash simpleHash = new SimpleHash(context, defaultObjectWrapper);
    String sql = parse(COMPLEX_MODEL_TEMPLATE, simpleHash);

    assertEquals(COMPLEX_MODEL_SQL, sql);
  }


  String parse(String template, Object context) {
    return FreeMakerParser.process(template, context);
  }

  public class Account {

    private String contactName;

    private String firstName;

    public String getContactName() {
      return contactName;
    }

    public void setContactName(String contactName) {
      this.contactName = contactName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }
  }
}
