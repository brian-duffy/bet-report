package com.bet.report.validators;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.bet.report.BetReportRequest;
import com.bet.test.data.PopulatedBetReport;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@RunWith(SpringRunner.class)
public class BetValidatorTest {

  @InjectMocks
  private BetValidator betValidator;

  @MockBean
  private SpringValidatorAdapter springValidatorAdapter;
  private BetReportRequest betReportRequest;

  @Before
  public void before() {
    openMocks(this);
    betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build()).build();
  }

  @Test
  public void shouldThrowValidationExceptionWhenInvalidRequest() {
    ConstraintViolation exception = mock(ConstraintViolation.class);
    Set<ConstraintViolation<Object>> violations = new HashSet<>();
    violations.add(exception);
    when(springValidatorAdapter.validate(any())).thenReturn(violations);
    betReportRequest.getBets().get(0).setPrice(null);
    assertThrows(ConstraintViolationException.class, () -> betValidator.validate(betReportRequest));
  }
}
