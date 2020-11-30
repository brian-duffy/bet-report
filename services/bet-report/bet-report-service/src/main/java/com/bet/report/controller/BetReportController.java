package com.bet.report.controller;

import com.bet.report.BetReportRequest;
import com.bet.report.CurrencyReport;
import com.bet.report.SelectionReport;
import com.bet.report.service.CurrencyReportService;
import com.bet.report.service.SelectionReportService;
import com.bet.utils.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller to handle public methods for service.
 */
@RestController
@Validated
@Slf4j
public class BetReportController {

  @Autowired
  private SelectionReportService selectionReportService;

  @Autowired
  private CurrencyReportService currencyReportService;

  /**
   * Convert from a csv file to {@link SelectionReport}.
   *
   * @param file the csv
   * @return the response entity
   */
  @PostMapping(value = "/reports/selection/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful Response",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = SelectionReport.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid Request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
  )
  public ResponseEntity<List<SelectionReport>> selectionCsvReport(
      @RequestPart(value = "file") MultipartFile file) {
    log.info("Calculating selection report for csv");
    List<SelectionReport> selectionReports = selectionReportService.generate(file);
    log.info("Successfully calculated report for csv with {} results", selectionReports.size());
    return ResponseEntity.ok(selectionReports);
  }

  /**
   * Convert from {@link BetReportRequest} to {@link SelectionReport}.
   *
   * @param betReportRequest the bets
   * @return the response entity
   */
  @PostMapping(value = "/reports/selection", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful Response",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = SelectionReport.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid Request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
  )
  public ResponseEntity<List<SelectionReport>> selectionReport(
      @RequestBody @Valid BetReportRequest betReportRequest) {
    log.info("Calculating selection report");
    List<SelectionReport> selectionReports = selectionReportService.generate(betReportRequest);
    log.info("Successfully calculated selection report with {} results", selectionReports.size());
    return ResponseEntity.ok(selectionReports);
  }

  /**
   * Convert from {@link BetReportRequest} to {@link CurrencyReport}.
   *
   * @param betReportRequest the bets
   * @return the response entity
   */
  @PostMapping(value = "/reports/currency", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful Response",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyReport.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid Request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
  )
  public ResponseEntity<List<CurrencyReport>> currencyReport(
      @RequestBody @Valid BetReportRequest betReportRequest) {
    log.info("Calculating currency report");
    List<CurrencyReport> currencyReports = currencyReportService.generate(betReportRequest);
    log.info("Successfully calculated currency report with {} results", currencyReports.size());
    return ResponseEntity.ok(currencyReports);
  }


  /**
   * Convert from a csv file to {@link CurrencyReport}.
   *
   * @param file the csv
   * @return the response entity
   */
  @PostMapping(value = "/reports/currency/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful Response",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyReport.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid Request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal Server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
  )
  public ResponseEntity<List<CurrencyReport>> currencyCsvReport(
      @RequestPart(value = "file") MultipartFile file) {
    log.info("Calculating currency report for csv");
    List<CurrencyReport> currencyReports = currencyReportService.generate(file);
    log.info("Successfully calculated currency csv report with {} results",
        currencyReports.size());
    return ResponseEntity.ok(currencyReports);
  }

}
