package com.bet.report.service;

import com.bet.report.BetReportRequest;
import com.bet.report.CurrencyReport;
import com.bet.report.enums.Currency;
import com.bet.report.validators.BetValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Currency report service.
 */
@Service
public class CurrencyReportService extends AbstractReportingService<CurrencyReport> {

  @Autowired
  private BetValidator betValidator;

  @Override
  public List<CurrencyReport> generate(BetReportRequest betReportRequest) {
    betValidator.validate(betReportRequest);
    HashMap<Currency, CurrencyReport> map = new HashMap<>();
    betReportRequest.getBets().forEach(bet -> {
      if (map.containsKey(bet.getCurrency())) {
        CurrencyReport currencyReport = map.get(bet.getCurrency());
        currencyReport.increaseNumberOfBets();
        currencyReport.increaseStake(bet.getStake());
        currencyReport.increaseTotalLiability(bet);
      } else {
        map.put(bet.getCurrency(), new CurrencyReport(bet));
      }
    });
    return new ArrayList<>(map.values());
  }
}
