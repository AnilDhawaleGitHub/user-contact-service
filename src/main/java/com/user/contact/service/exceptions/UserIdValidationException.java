package com.user.contact.service.exceptions;

public class UserIdValidationException extends RuntimeException {

  private String msg;

  public UserIdValidationException(String msg) {
    super(msg);
    this.msg = msg;
  }
}
