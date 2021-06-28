package org.blab.mde.core.spring;

import java.lang.annotation.Annotation;

import org.blab.mde.core.annotation.CompositeDecorator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.RestController;


public class RestControllerDecorator implements CompositeDecorator {
  @Override
  public Annotation[] annotatedWith() {
    return new Annotation[]{
        new RestController() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return RestController.class;
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
