package org.blab.mde.demo.act.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import org.blab.mde.eve.spring.SpringEventMixin;
import org.blab.mde.svc.annotation.Service;


@Service
public interface AccountService extends SpringEventMixin {
  Logger log = LoggerFactory.getLogger(AccountService.class);

  @PostConstruct
  default void init() {
    register(AccountService.class);
  }


  default void ping() {
    log.info("pong");
  }
}
