package org.blab.mde.core.exception;

import java.util.List;

/**
 * A crashed exception is runtime exception that can't recover the execution by client.
 * Crashed exceptions should declare in Javadoc with @throws tag rather than declare in
 * method signature with throws keyword.
 */
public class CrashedException extends RuntimeException {
  public CrashedException() {
  }

  public CrashedException(String message, Object... args) {
    this(message, null, args);
  }

  public CrashedException(String message, Throwable cause, Object... args) {
    super(ExceptionUtil.format(message, args), cause);
  }

  public CrashedException(Throwable cause) {
    super(cause);
  }

  public CrashedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public CrashedException(List<String> messages) {
    this(messages, null);
  }

  public CrashedException(List<String> messages, Throwable cause) {
    this(ExceptionUtil.flatten(messages), cause);
  }
}
