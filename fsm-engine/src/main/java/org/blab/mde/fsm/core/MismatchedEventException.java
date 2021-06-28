package org.blab.mde.fsm.core;

import java.util.List;

import org.blab.mde.core.exception.CrashedException;


public class MismatchedEventException extends CrashedException {
  public MismatchedEventException() {
  }

  public MismatchedEventException(String message, Object... args) {
    super(message, args);
  }

  public MismatchedEventException(String message, Throwable cause, Object... args) {
    super(message, cause, args);
  }

  public MismatchedEventException(Throwable cause) {
    super(cause);
  }

  public MismatchedEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MismatchedEventException(List<String> messages) {
    super(messages);
  }

  public MismatchedEventException(List<String> messages, Throwable cause) {
    super(messages, cause);
  }
}
