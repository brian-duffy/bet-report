package com.bet.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type System exception controller advice.
 */
@ControllerAdvice
@Slf4j
public class SystemExceptionControllerAdvice {

  /**
   * The constant AREA.
   */
  public static final String AREA = "General";

  /**
   * Handle system exception response entity.
   *
   * @param systemException the system exception
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({SystemException.class})
  @ResponseBody
  public ResponseEntity handleSystemException(SystemException systemException) {
    log.error("A System Exception has occurred. ", systemException);
    if (systemException.getMessage() != null) {
      log.error("{}", systemException.getMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildErrorResponse(systemException.getMessage()));
  }

  private ErrorResponse buildErrorResponse(String message) {
    return ErrorResponse.builder().area(AREA).message(message).build();
  }
}
