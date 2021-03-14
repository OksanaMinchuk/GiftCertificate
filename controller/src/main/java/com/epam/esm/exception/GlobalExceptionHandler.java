package com.epam.esm.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = Logger.getRootLogger();

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> entityNotFoundExceptionHandler(EntityNotFoundException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(WrongDataException.class)
  public ResponseEntity<?> wrongDataExceptionHandler(WrongDataException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataRepositoryException.class)
  public ResponseEntity<?> dataRepositoryExceptionHandler(DataRepositoryException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataServiceException.class)
  public ResponseEntity<?> dataServiceExceptionHandler(DataServiceException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex) {
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Global Exception:");
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IncorrectParameterException.class)
  public ResponseEntity<?> incorrectParameterException(IncorrectParameterException ex) {
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage(ex.getMessage());
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Method Not Supported");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Content type not supported");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    ErrorDetails errorDetails = new ErrorDetails();
    fieldErrors.forEach(error -> errorDetails.addMessage(error.getDefaultMessage()));
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("No Handler Found Exception: " + request.getDescription(false));
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Http Message Not Readable");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Access is denied.");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Invalid_token. Access token expired. Access is denied.");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidGrantException.class)
  public ResponseEntity<Object> handleInvalidGrantException(InvalidGrantException ex) {

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.addMessage("Invalid_grant_token. Access token expired. Access is denied.");
    LOGGER.error(ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }
}
