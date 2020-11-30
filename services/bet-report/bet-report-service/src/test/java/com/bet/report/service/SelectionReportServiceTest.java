package com.bet.report.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.openMocks;

import com.bet.report.Bet;
import com.bet.report.BetReportRequest;
import com.bet.report.SelectionReport;
import com.bet.report.enums.Currency;
import com.bet.report.utils.CsvToPojo;
import com.bet.report.validators.BetValidator;
import com.bet.test.data.PopulatedBetReport;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SelectionReportServiceTest {

  private static final String VALID_SAMPLE_CSV = "requests/valid-sample.csv";

  private static final String INVALID_FILE = "requests/invalid-file.xe";

  @InjectMocks
  private SelectionReportService selectionReportService;
  @MockBean
  private BetValidator betValidator;

  private BetReportRequest betReportRequest;

  @Before
  public void before() {
    openMocks(this);
    betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build()).build();
  }

  @Test
  public void whenConvertingToSelectionFromPojoExpectSuccessfulReport() {
    List<SelectionReport> reports = selectionReportService.generate(betReportRequest);
    assertThat(reports).size().isEqualTo(4);
    // Assert correct order
    assertThat(reports.get(0).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(0).getTotalLiability()).isEqualTo(new BigDecimal("24.75"));
    assertThat(reports.get(1).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(1).getTotalLiability()).isEqualTo(new BigDecimal("22.50"));

    assertThat(reports.get(2).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(2).getTotalLiability()).isEqualTo(new BigDecimal("3.00"));

    assertThat(reports.get(3).getCurrency()).isEqualTo(Currency.EUR);
    assertThat(reports.get(3).getTotalLiability()).isEqualTo(new BigDecimal("60.30"));

    // Assert total stakes is same
    BigDecimal totalTestData = betReportRequest.getBets().stream()
        .map(Bet::getStake)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalTestResult = reports.stream().map(SelectionReport::getTotalStakes)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    assertThat(totalTestData).isEqualTo(totalTestResult);
  }

  @Test
  public void whenConvertingToSelectionFromCsvExpectSuccessfulReport() throws IOException {
    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()).toURI())));
    List<SelectionReport> reports = selectionReportService
        .generate(file);
    assertThat(reports.size()).isEqualTo(8);
    // Assert correct order
    assertThat(reports.get(0).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(0).getTotalLiability()).isEqualTo(new BigDecimal("122.55"));

    assertThat(reports.get(1).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(1).getTotalLiability()).isEqualTo(new BigDecimal("109.40"));

    assertThat(reports.get(2).getCurrency()).isEqualTo(Currency.GBP);
    assertThat(reports.get(2).getTotalLiability()).isEqualTo(new BigDecimal("92.05"));

    assertThat(reports.get(4).getCurrency()).isEqualTo(Currency.EUR);
    assertThat(reports.get(4).getTotalLiability()).isEqualTo(new BigDecimal("138.15"));

    assertThat(reports.get(7).getCurrency()).isEqualTo(Currency.EUR);
    assertThat(reports.get(7).getTotalLiability()).isEqualTo(new BigDecimal("14.40"));

    // Assert total stakes same
    List<Bet> totalBets = CsvToPojo.from(file, Bet.class);
    BigDecimal totalTestData = totalBets.stream()
        .map(Bet::getStake)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalTestResult = reports.stream().map(SelectionReport::getTotalStakes)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    assertThat(totalTestData).isEqualTo(totalTestResult);
  }


  @Test
  public void whenConvertingToSelectionFromInvalidCsvExpectConstraintViolationException()
      throws IOException {
//    ConstraintViolation exception = mock(ConstraintViolation.class);
//    Set<ConstraintViolation<Object>> violations = new HashSet<>();
//    violations.add(exception);
//    when(springValidatorAdapter.validate(any())).thenReturn(violations);

    doThrow(ConstraintViolationException.class).when(betValidator).validate(any());
//    when(betValidator.validate(any()))
//        .thenThrow(ConstraintViolationException.class);

    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(INVALID_FILE).getFile()).toURI())));
    assertThrows(ConstraintViolationException.class, () -> selectionReportService
        .generate(file));
  }

  @Test
  public void whenConvertingToSelectionFromInvalidPojoExpectConstraintViolationException() {
    doThrow(ConstraintViolationException.class).when(betValidator).validate(any());
    betReportRequest.getBets().get(0).setPrice(null);
    assertThrows(ConstraintViolationException.class, () -> selectionReportService
        .generate(betReportRequest));
  }


}
