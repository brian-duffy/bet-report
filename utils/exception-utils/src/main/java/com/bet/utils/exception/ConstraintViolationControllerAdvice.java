package com.bet.utils.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Constraint violation controller advice.
 */
@ControllerAdvice
@Slf4j
public class ConstraintViolationControllerAdvice {

  /**
   * The constant FIELD_VALIDATION_FAILURE.
   */
  public static final String FIELD_VALIDATION_FAILURE = "Input validation failure";

  /**
   * The constant FIELD_VALIDATION_FAILURE_MESSAGE.
   */
  public static final String FIELD_VALIDATION_FAILURE_MESSAGE = "Missing or invalid data found in request";

  /**
   * Handle response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {

    ErrorResponse errorResponse = buildErrorResponse(e);

    log.error("Input validation failure, {}", errorResponse);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  /**
   * Handle validation failure response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({InvalidFormatException.class})
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleValidationFailure(InvalidFormatException e) {

    log.error("Json request cannot be converted to Object, {}", e.getMessage());
    ErrorResponse errorResponse = ErrorResponse.builder()
        .area(FIELD_VALIDATION_FAILURE)
        .message(FIELD_VALIDATION_FAILURE_MESSAGE)
        .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  /**
   * Handle system exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleSystemException(HttpMessageNotReadableException e) {
    log.error("Json request cannot be converted to Object, {}", e.getMessage());
    ErrorResponse errorResponse = ErrorResponse.builder()
        .area(FIELD_VALIDATION_FAILURE)
        .message(FIELD_VALIDATION_FAILURE_MESSAGE)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(ConstraintViolationException e) {
    Map<String, String> validationFailures = new HashMap<>();
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      validationFailures.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ErrorResponse.builder()
        .area(FIELD_VALIDATION_FAILURE)
        .message(FIELD_VALIDATION_FAILURE_MESSAGE)
        .validationErrors(validationFailures).build();
  }
}
