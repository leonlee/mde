package org.blab.mde.ee.dal;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.core.exception.CrashedException;

import static org.blab.mde.core.util.ClassUtil.getMethodType;
import static org.blab.mde.core.util.Guarder.unsupportedOperation;
import static org.apache.commons.lang3.reflect.TypeUtils.getRawType;

/**
 * Please use Dal instead of JdbiMixins.
 */

@Mixin
public interface JdbiMixin<Dao> {
  Logger log = LoggerFactory.getLogger(JdbiMixin.class);

  @Property
  @Inject
  Jdbi getJdbi();

  void setJdbi(Jdbi jdbi);

  /**
   * Only for dao type inference
   *
   * @return dao Type
   */
  default Dao _daoType() {
    return unsupportedOperation();
  }

  @SuppressWarnings("unchecked")
  default Class<Dao> _inferenceDaoType() {
    return (Class<Dao>) getRawType(getMethodType(getClass(), "_daoType"), getClass());
  }

  default <X extends Exception> void useDaoOnce(DaoConsumer<Dao, X> consumer) {
    try (Handle handle = getHandle()) {
      consumer.useDao(handle.attach(_inferenceDaoType()));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  default <D, X extends Exception> void useOnce(Class<D> type, DaoConsumer<D, X> consumer) {
    try (Handle handle = getHandle()) {
      consumer.useDao(handle.attach(type));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  default <R, X extends Exception> R withDaoOnce(DaoCallback<R, Dao, X> callback) {
    try (Handle handle = getHandle()) {
      return callback.with(handle.attach(_inferenceDaoType()));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  default <R, D, X extends Exception> R withOnce(Class<D> type, DaoCallback<R, D, X> callback) {
    try (Handle handle = getHandle()) {
      return callback.with(handle.attach(type));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  default Handle getHandle() {
    return getJdbi().open();
  }
}
