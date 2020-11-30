package com.bet.report.validators;

import com.bet.report.BetReportRequest;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * The type Bet validator.
 */
@Component
@Validated
public class BetValidator {

  @Autowired
  private SpringValidatorAdapter springValidatorAdapter;

  /**
   * Validate.
   *
   * @param request the request
   */
  public void validate(@Valid BetReportRequest request) {
    Set<ConstraintViolation<BetReportRequest>> violations = springValidatorAdapter
        .validate(request);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
