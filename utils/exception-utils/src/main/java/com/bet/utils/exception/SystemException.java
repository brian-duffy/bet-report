package com.bet.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type System exception.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
@Slf4j
public class SystemException extends RuntimeException {

  /**
   * Instantiates a new System exception.
   *
   * @param message the message
   */
  public SystemException(String message) {
    super(message);
    log.error(message);
  }
}
