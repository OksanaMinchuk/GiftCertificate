package com.epam.esm.exception;

public class DataRepositoryException extends RuntimeException {

  public DataRepositoryException() {}

  public DataRepositoryException(String message) {
    super(message);
  }

  public DataRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataRepositoryException(Throwable cause) {
    super(cause);
  }

  public DataRepositoryException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
