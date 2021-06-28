package org.blab.mde.core.exception;

import java.util.List;

/**
 * A recoverable exception should be caught by client to recover execution.<br/>
 */
public class RecoverableException extends Exception {
  public RecoverableException() {
  }

  public RecoverableException(String message, Object... args) {
    this(message, null, args);
  }

  public RecoverableException(String message, Throwable cause, Object... args) {
    super(ExceptionUtil.format(message, args), cause);
  }

  public RecoverableException(Throwable cause) {
    super(cause);
  }

  public RecoverableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public RecoverableException(List<String> messages) {
    this(messages, null);
  }

  public RecoverableException(List<String> messages, Throwable cause) {
    this(ExceptionUtil.flatten(messages), cause);
  }
}
