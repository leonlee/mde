package org.blab.mde.ee.dal;


@FunctionalInterface
public interface DaoConsumer<D, X extends Exception> {
  void useDao(D dao) throws X;
}
