package com.bet.report.service;

import static java.util.stream.Collectors.toList;

import com.bet.report.BetReportRequest;
import com.bet.report.Report;
import com.bet.report.SelectionReport;
import com.bet.report.enums.Currency;
import com.bet.report.enums.Selection;
import com.bet.report.validators.BetValidator;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Service one service.
 */
@Service
public class SelectionReportService extends AbstractReportingService<SelectionReport> {

  @Autowired
  private BetValidator betValidator;

  @Override
  public List<SelectionReport> generate(BetReportRequest betReportRequest) {
    betValidator.validate(betReportRequest);
    Table<Selection, Currency, SelectionReport> report = HashBasedTable.create();
    betReportRequest.getBets().forEach(bet -> {
      SelectionReport row = report.get(bet.getSelectionName(), bet.getCurrency());
      if (row != null) {
        row.increaseNumberOfBets();
        row.increaseTotalLiability(bet);
        row.increaseStake(bet.getStake());
      } else {
        report.put(bet.getSelectionName(), bet.getCurrency(), new SelectionReport(bet));
      }
    });
    return report.cellSet()
        .stream()
        .map(Table.Cell::getValue)
        .sorted(Comparator.comparing(Report::getCurrency).reversed()
            .thenComparing(Report::getTotalLiability).reversed()
        )
        .collect(toList());
  }
}
