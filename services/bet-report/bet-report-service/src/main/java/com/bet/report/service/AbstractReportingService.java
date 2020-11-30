package com.bet.report.service;

import com.bet.report.Bet;
import com.bet.report.BetReportRequest;
import com.bet.report.Report;
import com.bet.report.utils.CsvToPojo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Abstract reporting service.
 *
 * @param <T> the type parameter
 */
@Slf4j
public abstract class AbstractReportingService<T extends Report> implements ReportingService<T> {


  @Override
  public List<T> generate(MultipartFile file) {
    log.debug("Converting bet data from csv");
    List<Bet> bets = CsvToPojo.from(file, Bet.class);
    log.debug("Successfully converted {} bets", bets.size());
    return generate(BetReportRequest.builder().bets(bets).build());
  }

}
