package com.bet.test.data;

import static java.util.Arrays.asList;

import com.bet.report.CurrencyReport;
import com.bet.report.enums.Currency;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Populated currency report.
 */
public class PopulatedCurrencyReport {

  /**
   * Build list.
   *
   * @return the list
   */
  public static List<CurrencyReport> build() {
    List<CurrencyReport> report = new ArrayList<>();

    report.addAll(asList(
        CurrencyReport.builder()
            .numberOfBets(10)
            .currency(Currency.EUR)
            .totalLiability(new BigDecimal("100.02"))
            .totalStakes(new BigDecimal("50.00"))
            .build(),
        CurrencyReport.builder()
            .numberOfBets(4)
            .currency(Currency.GBP)
            .totalLiability(new BigDecimal("50.02"))
            .totalStakes(new BigDecimal("55.00"))
            .build()
    ));
    return report;
  }
}
