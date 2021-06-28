package org.blab.mde.ee.spring;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.spring4.JdbiUtil;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.ee.dal.DaoCallback;
import org.blab.mde.ee.dal.DaoConsumer;
import org.blab.mde.ee.dal.JdbiMixin;

/**
 * Please use Dal instead of JdbiMixins.
 */

@Mixin
public interface SpringJdbiMixin<Dao> extends JdbiMixin<Dao> {
  @Override
  default Handle getHandle() {
    return JdbiUtil.getHandle(getJdbi());
  }

  default <X extends Exception> void useDao(DaoConsumer<Dao, X> consumer) {
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      try {
        consumer.useDao(getHandle().attach(_inferenceDaoType()));
      } catch (Exception x) {
        log.error("can't invoke dao method", x);
        throw new CrashedException(x);
      }
    } else {
      useDaoOnce(consumer);
    }
  }

  default <D, X extends Exception> void use(Class<D> type, DaoConsumer<D, X> consumer) {
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      try {
        consumer.useDao(getHandle().attach(type));
      } catch (Exception x) {
        log.error("can't invoke dao method", x);
        throw new CrashedException(x);
      }
    } else {
      useOnce(type, consumer);
    }
  }

  default <R, X extends Exception> R withDao(DaoCallback<R, Dao, X> callback) {
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      try {
        return callback.with(getHandle().attach(_inferenceDaoType()));
      } catch (Exception x) {
        log.error("can't invoke dao method", x);
        throw new CrashedException(x);
      }
    } else {
      return withDaoOnce(callback);
    }
  }

  default <R, D, X extends Exception> R with(Class<D> type, DaoCallback<R, D, X> callback) {
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      try {
        return callback.with(getHandle().attach(type));
      } catch (Exception x) {
        log.error("can't invoke dao method", x);
        throw new CrashedException(x);
      }
    } else {
      return withOnce(type, callback);
    }
  }
}
