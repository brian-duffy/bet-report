package com.bet.utils.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Exception configuration.
 */
@Configuration
public class ExceptionConfiguration {

  /**
   * Constraint violation controller advice constraint violation controller advice.
   *
   * @return the constraint violation controller advice
   */
  @Bean
  public ConstraintViolationControllerAdvice constraintViolationControllerAdvice() {
    return new ConstraintViolationControllerAdvice();
  }

  /**
   * System exception controller advice system exception controller advice.
   *
   * @return the system exception controller advice
   */
  @Bean
  public SystemExceptionControllerAdvice systemExceptionControllerAdvice() {
    return new SystemExceptionControllerAdvice();
  }

}
