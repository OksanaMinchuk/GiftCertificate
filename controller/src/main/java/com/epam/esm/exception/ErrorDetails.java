package com.epam.esm.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorDetails {

  private List<String> errorMessage;

  public ErrorDetails() {}

  public List<String> getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(List<String> errorMessage) {
    this.errorMessage = errorMessage;
  }

  public void addMessage(String message) {
    if (errorMessage == null) {
      errorMessage = new ArrayList<>();
    }
    errorMessage.add(message);
  }
}
