package org.blab.mde.demo.act.dal;

import org.blab.mde.demo.act.dal.dao.ContactDao;
import org.blab.mde.demo.act.model.Contact;
import org.blab.mde.ee.annotation.Repository;
import org.blab.mde.ee.spring.SpringJdbiMixin;


@Repository
public interface ContactRepository extends SpringJdbiMixin<ContactDao> {

  default long save(Contact contact) {
    return with(ContactDao.class, dao -> dao.insert(contact));
  }


  default int update(Contact contact) {
    return with(ContactDao.class, dao -> dao.update(contact));
  }

  default Contact selectById(long id) {
    return with(ContactDao.class, dao -> dao.selectById(id));
  }
}
