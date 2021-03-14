package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectParameterException extends Exception {

  private static final long serialVersionUID = 1L;

  public IncorrectParameterException(String message) {
    super(message);
  }
}
