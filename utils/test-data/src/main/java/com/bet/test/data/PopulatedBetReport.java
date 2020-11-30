package com.bet.test.data;

import static java.util.Arrays.asList;

import com.bet.report.Bet;
import com.bet.report.enums.Currency;
import com.bet.report.enums.Selection;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Populated bet report.
 */
public class PopulatedBetReport {

  /**
   * Build list.
   *
   * @return the list
   */
  public static List<Bet> build() {
    List<Bet> bets = new ArrayList<>();
    bets.addAll(asList(
        Bet.builder()
            .betId("10")
            .betTimestamp("1489490156000")
            .selectionId(1)
            .selectionName(Selection.MY_FAIR_LADY)
            .stake(new BigDecimal("0.5"))
            .price(new BigDecimal("6.0"))
            .currency(Currency.GBP)
            .build(),
        Bet.builder()
            .betId("12")
            .betTimestamp("1489490156000")
            .selectionId(3)
            .selectionName(Selection.BILBOS_ADVENTURE)
            .stake(new BigDecimal("5.0"))
            .price(new BigDecimal("4.5"))
            .currency(Currency.GBP)
            .build(),
        Bet.builder()
            .betId("13")
            .betTimestamp("1489490156000")
            .selectionId(4)
            .selectionName(Selection.FAN_THE_FLAMES)
            .stake(new BigDecimal("4.5"))
            .price(new BigDecimal("5.5"))
            .currency(Currency.GBP)
            .build(),
        Bet.builder()
            .betId("14")
            .betTimestamp("1489490156000")
            .selectionId(5)
            .selectionName(Selection.ALWAYS_A_RUNNER)
            .stake(new BigDecimal("7.9"))
            .price(new BigDecimal("7.0"))
            .currency(Currency.EUR)
            .build(),
        Bet.builder()
            .betId("11")
            .betTimestamp("1489490156000")
            .selectionId(2)
            .selectionName(Selection.ALWAYS_A_RUNNER)
            .stake(new BigDecimal("1.25"))
            .price(new BigDecimal("4.0"))
            .currency(Currency.EUR)
            .build()
        )
    );

    return bets;
  }
}
