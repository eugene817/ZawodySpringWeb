package com.zawodyweb.zawodyweb.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
  private final HttpStatus status;

  public BusinessException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public BusinessException(String message) {
    super(message);
    this.status = HttpStatus.CONFLICT;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
