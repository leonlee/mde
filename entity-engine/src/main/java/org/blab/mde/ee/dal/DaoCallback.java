package org.blab.mde.ee.dal;


@FunctionalInterface
public interface DaoCallback<R, D, X extends Exception> {
  R with(D dao) throws X;
}
