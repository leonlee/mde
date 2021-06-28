package org.blab.mde.ee.spring;

import com.google.common.base.Strings;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.spring4.JdbiUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.blab.mde.core.exception.CrashedException;
import org.blab.mde.core.spring.SpringHolder;
import org.blab.mde.ee.dal.DaoCallback;
import org.blab.mde.ee.dal.DaoConsumer;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static org.blab.mde.core.util.Guarder.requireNotNull;
import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;
import static org.springframework.transaction.TransactionDefinition.TIMEOUT_DEFAULT;


@Slf4j
@UtilityClass
public class Dal {
  private static Jdbi jdbi;
  private static TransactionTemplate template;
  private static PlatformTransactionManager txManager;

  public static <V> V inTx(Callable<V> callable) {
    return getTemplate().execute(transactionStatus -> {
      try {
        return callable.call();
      } catch (Exception e) {
        throw new CrashedException(e);
      }
    });
  }

  public static void inTx(VoidCallable callable) {
    getTemplate().execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
        callable.call();
      }
    });
  }

  public static void inTx(TransactionSetting setting, VoidCallable callable) {
    TransactionStatus status = getTxManager().getTransaction(setting.toDefinition());
    try {
      callable.call();
      txManager.commit(status);
    } catch (Throwable e) {
      if (setting.noRollbackFor.contains(e.getClass())) {
        getTxManager().commit(status);
        throw e;
      }
      if (setting.rollbackFor.contains(e.getClass())) {
        getTxManager().rollback(status);
        throw e;
      }
      getTxManager().rollback(status);
      throw e;
    }
  }

  public static <D, X extends Exception> void use(Class<D> type, DaoConsumer<D, X> consumer) {
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

  public static <R, D, X extends Exception> R with(Class<D> type, DaoCallback<R, D, X> callback) {
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


  public static <D, X extends Exception> void useOnce(Class<D> type, DaoConsumer<D, X> consumer) {
    try (Handle handle = getHandle()) {
      consumer.useDao(handle.attach(type));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  public static <R, D, X extends Exception> R withOnce(Class<D> type, DaoCallback<R, D, X> callback) {
    try (Handle handle = getHandle()) {
      return callback.with(handle.attach(type));
    } catch (Exception x) {
      log.error("can't invoke dao method", x);
      throw new CrashedException(x);
    }
  }

  private static synchronized Jdbi getJdbi() {
    if (jdbi == null) {
      jdbi = SpringHolder.getBean(Jdbi.class);
      requireNotNull(jdbi);
    }

    return jdbi;
  }

  private static Handle getHandle() {
    return JdbiUtil.getHandle(getJdbi());
  }

  private static synchronized TransactionTemplate getTemplate() {
    if (template == null) {
      template = new TransactionTemplate(getTxManager());
    }

    return template;
  }

  private static synchronized PlatformTransactionManager getTxManager() {
    if (txManager == null) {
      txManager = SpringHolder.getBean(PlatformTransactionManager.class);
      requireNotNull(txManager);
    }

    return txManager;
  }

  @Builder
  public static class TransactionSetting {
    private String name;
    @Builder.Default
    private int isolation = ISOLATION_DEFAULT;
    @Builder.Default
    private int propagation = PROPAGATION_REQUIRED;
    @Builder.Default
    private int timeout = TIMEOUT_DEFAULT;
    @Builder.Default
    private boolean readOnly = false;

    @Builder.Default
    private List<Class<? extends Throwable>> rollbackFor = new ArrayList<>();
    @Builder.Default
    private List<Class<? extends Throwable>> noRollbackFor = new ArrayList<>();

    TransactionDefinition toDefinition() {
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

      if (!Strings.isNullOrEmpty(name)) {
        definition.setName(name);
      }

      if (isolation != ISOLATION_DEFAULT) {
        definition.setIsolationLevel(isolation);
      }

      if (timeout != TIMEOUT_DEFAULT) {
        definition.setTimeout(timeout);
      }

      definition.setPropagationBehavior(propagation);
      definition.setReadOnly(readOnly);

      return definition;
    }
  }
}
