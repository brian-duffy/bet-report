package com.bet.report.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.openMocks;

import com.bet.report.BetReportRequest;
import com.bet.report.CurrencyReport;
import com.bet.report.enums.Currency;
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

/**
 * The type Currency report service test.
 */
@RunWith(SpringRunner.class)
public class CurrencyReportServiceTest {

  private static final String VALID_SAMPLE_CSV = "requests/valid-sample.csv";

  private static final String INVALID_FILE = "requests/invalid-file.xe";
  @InjectMocks
  private CurrencyReportService currencyReportService;
  @MockBean
  private BetValidator betValidator;
  private BetReportRequest betReportRequest;

  /**
   * Before.
   */
  @Before
  public void before() {
    openMocks(this);
    betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build()).build();
  }

  /**
   * When converting to currency from pojo expect successful report.
   */
  @Test
  public void whenConvertingToCurrencyFromPojoExpectSuccessfulReport() {
    List<CurrencyReport> reports = currencyReportService.generate(betReportRequest);
    assertThat(reports).size().isEqualTo(2);
    CurrencyReport euroReport = reports.stream().filter(x -> Currency.EUR.equals(x.getCurrency()))
        .findFirst().get();
    assertThat(euroReport).isNotNull();
    assertThat(euroReport.getNumberOfBets()).isEqualTo(2);
    assertThat(euroReport.getTotalStakes()).isEqualTo(new BigDecimal("9.15"));
    assertThat(euroReport.getTotalLiability()).isEqualTo(new BigDecimal("60.30"));
    CurrencyReport gbpReport = reports.stream().filter(x -> Currency.GBP.equals(x.getCurrency()))
        .findFirst().get();
    assertThat(gbpReport).isNotNull();
    assertThat(gbpReport.getNumberOfBets()).isEqualTo(3);
    assertThat(gbpReport.getTotalStakes()).isEqualTo(new BigDecimal("10.00"));
    assertThat(gbpReport.getTotalLiability()).isEqualTo(new BigDecimal("50.25"));
  }

  /**
   * When converting to currency from csv expect successful report.
   *
   * @throws IOException the io exception
   */
  @Test
  public void whenConvertingToCurrencyFromCsvExpectSuccessfulReport() throws IOException {
    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()).toURI())));
    List<CurrencyReport> reports = currencyReportService
        .generate(file);
    assertThat(reports.size()).isEqualTo(2);
    CurrencyReport euroReport = reports.stream().filter(x -> Currency.EUR.equals(x.getCurrency()))
        .findFirst().get();
    assertThat(euroReport.getNumberOfBets()).isEqualTo(11);
    assertThat(euroReport.getTotalStakes()).isEqualTo(new BigDecimal("38.25"));
    assertThat(euroReport.getTotalLiability()).isEqualTo(new BigDecimal("248.90"));
    CurrencyReport gbpReport = reports.stream().filter(x -> Currency.GBP.equals(x.getCurrency()))
        .findFirst().get();
    assertThat(gbpReport.getNumberOfBets()).isEqualTo(13);
    assertThat(gbpReport.getTotalStakes()).isEqualTo(new BigDecimal("61.10"));
    assertThat(gbpReport.getTotalLiability()).isEqualTo(new BigDecimal("364.10"));
  }

  /**
   * When converting to currency from invalid csv expect constraint violation exception.
   *
   * @throws IOException the io exception
   */
  @Test
  public void whenConvertingToCurrencyFromInvalidCsvExpectConstraintViolationException()
      throws IOException {
    doThrow(ConstraintViolationException.class).when(betValidator).validate(any());
    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(INVALID_FILE).getFile()).toURI())));
    assertThrows(ConstraintViolationException.class, () -> currencyReportService
        .generate(file));
  }


}
