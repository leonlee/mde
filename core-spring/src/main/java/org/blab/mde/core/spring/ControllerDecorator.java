package org.blab.mde.core.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;

import org.blab.mde.core.annotation.CompositeDecorator;


public class ControllerDecorator implements CompositeDecorator {
  @Override
  public Annotation[] annotatedWith() {
    return new Annotation[]{
        new Controller() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return Controller.class;
          }

          @Override
          public String value() {
            return "";
          }
        },
        new Scope() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return Scope.class;
          }

          @Override
          public String value() {
            return scopeName();
          }

          @Override
          public String scopeName() {
            return BeanDefinition.SCOPE_SINGLETON;
          }

          @Override
          public ScopedProxyMode proxyMode() {
            return ScopedProxyMode.DEFAULT;
          }
        }
    };
  }
}
