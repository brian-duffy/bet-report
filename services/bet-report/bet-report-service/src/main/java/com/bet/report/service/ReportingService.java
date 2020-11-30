package com.bet.report.service;

import com.bet.report.BetReportRequest;
import com.bet.report.Report;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Reporting service.
 *
 * @param <T> the type parameter
 */
public interface ReportingService<T extends Report> {

  /**
   * Generate list.
   *
   * @param file the file
   * @return the list
   */
  List<T> generate(MultipartFile file);

  /**
   * Generate list.
   *
   * @param data the data
   * @return the list
   */
  List<T> generate(BetReportRequest data);

}
