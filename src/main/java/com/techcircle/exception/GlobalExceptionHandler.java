package com.techcircle.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // Custom error response class
  @Data
  @AllArgsConstructor
  public static class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String path;
    private int status;
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
    UserAlreadyExistsException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      ex.getMessage(),
      "USER_EXISTS",
      request.getDescription(false),
      HttpStatus.CONFLICT.value()
    );
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFound(
    UsernameNotFoundException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      ex.getMessage(),
      "USER_NOT_FOUND",
      request.getDescription(false),
      HttpStatus.NOT_FOUND.value()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(
    BadCredentialsException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      "Invalid username or password",
      "INVALID_CREDENTIALS",
      request.getDescription(false),
      HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidToken(
    InvalidTokenException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      ex.getMessage(),
      "INVALID_TOKEN",
      request.getDescription(false),
      HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(PasswordResetException.class)
  public ResponseEntity<ErrorResponse> handlePasswordReset(
    PasswordResetException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      ex.getMessage(),
      "PASSWORD_RESET_ERROR",
      request.getDescription(false),
      HttpStatus.BAD_REQUEST.value()
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(
    ResourceNotFoundException ex,
    WebRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      ex.getMessage(),
      "RESOURCE_NOT_FOUND",
      request.getDescription(false),
      HttpStatus.NOT_FOUND.value()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatusCode status,
    WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", "Validation Failed");
    body.put("path", request.getDescription(false));
    body.put("details", errors);

    return new ResponseEntity<>(body, headers, status);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllUncaughtException(
    Exception ex,
    WebRequest request) {
    log.error("Unexpected error occurred", ex);
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      "An unexpected error occurred",
      "INTERNAL_SERVER_ERROR",
      request.getDescription(false),
      HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}