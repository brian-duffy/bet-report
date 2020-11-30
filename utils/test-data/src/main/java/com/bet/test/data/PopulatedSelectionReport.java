package com.bet.test.data;

import static java.util.Arrays.asList;

import com.bet.report.SelectionReport;
import com.bet.report.enums.Currency;
import com.bet.report.enums.Selection;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Populated selection report.
 */
public class PopulatedSelectionReport {

  /**
   * Build list.
   *
   * @return the list
   */
  public static List<SelectionReport> build() {

    List<SelectionReport> report = new ArrayList<>(asList(
        SelectionReport.builder().name(Selection.ALWAYS_A_RUNNER)
            .numberOfBets(10)
            .currency(Currency.EUR)
            .totalLiability(new BigDecimal("100.02"))
            .totalStakes(new BigDecimal("50.00"))
            .build(),
        SelectionReport.builder().name(Selection.BILBOS_ADVENTURE)
            .numberOfBets(4)
            .currency(Currency.EUR)
            .totalLiability(new BigDecimal("50.02"))
            .totalStakes(new BigDecimal("55.00"))
            .build(),
        SelectionReport.builder().name(Selection.FAN_THE_FLAMES)
            .numberOfBets(2)
            .currency(Currency.GBP)
            .totalLiability(new BigDecimal("10.02"))
            .totalStakes(new BigDecimal("60.00"))
            .build()
    ));
    return report;
  }
}
