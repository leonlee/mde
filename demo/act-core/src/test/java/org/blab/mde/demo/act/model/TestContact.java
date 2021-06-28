package org.blab.mde.demo.act.model;

import org.junit.Assert;

import java.time.LocalDateTime;

import org.blab.mde.core.spring.SpringHolder;


//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AccountingServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = {"management.port=0"})
public class TestContact {

//  @Test
  public void testSave() {
    Contact contact = SpringHolder.getBean(Contact.class);

    buildField(contact);
    boolean saveResult = contact.save();

    Assert.assertTrue(saveResult);
  }

  private void buildField(Contact contact) {

    contact.setName("zhangSan");
    contact.setAddress("alibaba");
    contact.setQq("1234567");
    contact.setCreatedBy(350);
    contact.setCreatedOn(LocalDateTime.now());
    contact.setUpdatedBy(110880);
    contact.setUpdatedOn(LocalDateTime.now());

  }

//  @Test
  public void testSelectById() {
    Contact contact = Contact.selectById(1);

    Assert.assertEquals("zhangSan", contact.getName());
  }
}
