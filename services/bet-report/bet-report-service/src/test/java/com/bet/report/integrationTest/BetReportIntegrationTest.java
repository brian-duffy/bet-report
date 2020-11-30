package com.bet.report.integrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import com.bet.report.BetReportApplication;
import com.bet.report.BetReportConfiguration;
import com.bet.report.BetReportRequest;
import com.bet.test.data.PopulatedBetReport;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.File;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type Bet report integration test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BetReportConfiguration.class})
@SpringBootTest(classes = BetReportApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BetReportIntegrationTest {

  private static final String CURRENCY_REPORT_URL = "/reports/currency";

  private static final String CURRENCY_CSV_REPORT_URL = "/reports/currency/csv";

  private static final String SELECTION_REPORT_URL = "/reports/selection";

  private static final String SELECTION_CSV_REPORT_URL = "/reports/selection/csv";

  private static final String VALID_SAMPLE_CSV = "requests/valid-sample.csv";

  private static final String INVALID_SAMPLE_CSV = "requests/invalid-sample.csv";

  @LocalServerPort
  private int port;

  /**
   * Sets .
   */
  @Before
  public void setup() {
    RestAssured.port = port;
  }

  /**
   * When posting currency report with valid request expect successful response.
   */
  @Test
  public void whenPostingCurrencyReportWithValidRequestExpectSuccessfulResponse() {
    given().contentType(ContentType.JSON)
        .body(BetReportRequest.builder().bets(PopulatedBetReport.build()).build())
        .post(CURRENCY_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(2));
  }

  /**
   * When posting currency report with valid csv expect successful response.
   */
  @Test
  public void whenPostingCurrencyReportWithValidCSVExpectSuccessfulResponse() {
    given().multiPart(new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()))
        .post(CURRENCY_CSV_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(2));
  }

  /**
   * When posting selection report with valid request expect successful response.
   */
  @Test
  public void whenPostingSelectionReportWithValidRequestExpectSuccessfulResponse() {
    given().contentType(ContentType.JSON)
        .body(BetReportRequest.builder().bets(PopulatedBetReport.build()).build())
        .post(SELECTION_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  /**
   * When posting selection report with valid csv expect successful response.
   */
  @Test
  public void whenPostingSelectionReportWithValidCSVExpectSuccessfulResponse() {
    given().multiPart(new File(getClass().getClassLoader().getResource(VALID_SAMPLE_CSV).getFile()))
        .post(SELECTION_CSV_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  /**
   * When posting selection report with invalid csv expect successful response.
   */
  @Test
  public void whenPostingSelectionReportWithInvalidCSVExpectSuccessfulResponse() {
    given()
        .multiPart(new File(getClass().getClassLoader().getResource(INVALID_SAMPLE_CSV).getFile()))
        .post(SELECTION_CSV_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  /**
   * When posting currency report with invalid csv expect successful response.
   */
  @Test
  public void whenPostingCurrencyReportWithInvalidCSVExpectSuccessfulResponse() {
    given()
        .multiPart(new File(getClass().getClassLoader().getResource(INVALID_SAMPLE_CSV).getFile()))
        .post(CURRENCY_CSV_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void whenPostingCurrencyReportWithInvalidRequestExpectSuccessfulResponse() {
    BetReportRequest betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build())
        .build();
    betReportRequest.getBets().get(0).setBetId(null);
    betReportRequest.getBets().get(0).setSelectionName(null);
    given().contentType(ContentType.JSON)
        .body(betReportRequest)
        .post(CURRENCY_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void whenPostingSelectionReportWithInvalidRequestExpectSuccessfulResponse() {
    BetReportRequest betReportRequest = BetReportRequest.builder().bets(PopulatedBetReport.build())
        .build();
    betReportRequest.getBets().get(0).setBetId(null);
    betReportRequest.getBets().get(0).setSelectionName(null);
    given().contentType(ContentType.JSON)
        .body(betReportRequest)
        .post(SELECTION_REPORT_URL)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }
}
