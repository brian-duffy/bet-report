package com.bet.report.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.bet.report.BetReportRequest;
import com.bet.report.CurrencyReport;
import com.bet.report.SelectionReport;
import com.bet.report.service.CurrencyReportService;
import com.bet.report.service.SelectionReportService;
import com.bet.test.data.PopulatedBetReport;
import com.bet.test.data.PopulatedCurrencyReport;
import com.bet.test.data.PopulatedSelectionReport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Bet report controller test.
 */
@RunWith(SpringRunner.class)
public class BetReportControllerTest {

  private static final String VALID_SAMPLE_CSV = "requests/valid-sample.csv";

  @InjectMocks
  private BetReportController betReportController;

  @MockBean
  private CurrencyReportService currencyReportService;

  @MockBean
  private SelectionReportService selectionReportService;

  private BetReportRequest betReportRequest;

  private List<SelectionReport> populatedSelectionReport;

  private List<CurrencyReport> populatedCurrencyReport;

  /**
   * Before.
   */
  @Before
  public void before() {
    openMocks(this);
    betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build()).build();
    populatedSelectionReport = PopulatedSelectionReport.build();
    populatedCurrencyReport = PopulatedCurrencyReport.build();
  }

  /**
   * When performing selection csv report expect successful response.
   *
   * @throws IOException the io exception
   */
  @Test
  public void whenPerformingSelectionCsvReportExpectSuccessfulResponse() throws IOException {
    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()).toURI())));
    when(selectionReportService.generate(any(MultipartFile.class)))
        .thenReturn(populatedSelectionReport);
    ResponseEntity<List<SelectionReport>> responseEntity = betReportController
        .selectionCsvReport(file);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).size().isEqualTo(3);
  }

  /**
   * When performing selection report expect successful response.
   */
  @Test
  public void whenPerformingSelectionReportExpectSuccessfulResponse() {
    when(selectionReportService.generate(any(BetReportRequest.class)))
        .thenReturn(populatedSelectionReport);
    ResponseEntity<List<SelectionReport>> responseEntity = betReportController
        .selectionReport(betReportRequest);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).size().isEqualTo(3);
  }

  /**
   * When performing currency csv report expect successful response.
   *
   * @throws IOException the io exception
   */
  @Test
  public void whenPerformingCurrencyCsvReportExpectSuccessfulResponse() throws IOException {
    MockMultipartFile file = new MockMultipartFile("data", Files.readAllBytes(Paths.get(
        new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()).toURI())));
    when(currencyReportService.generate(any(MultipartFile.class)))
        .thenReturn(populatedCurrencyReport);
    ResponseEntity<List<CurrencyReport>> responseEntity = betReportController
        .currencyCsvReport(file);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).size().isEqualTo(2);
  }

  /**
   * When performing currency report expect successful response.
   */
  @Test
  public void whenPerformingCurrencyReportExpectSuccessfulResponse() {
    when(currencyReportService.generate(any(BetReportRequest.class)))
        .thenReturn(populatedCurrencyReport);
    ResponseEntity<List<CurrencyReport>> responseEntity = betReportController
        .currencyReport(betReportRequest);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).size().isEqualTo(2);
  }
}