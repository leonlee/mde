package org.blab.mde.core.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public enum ValidatorHolder {
  INSTANCE;
  public Validator validator;

  ValidatorHolder() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }
}
