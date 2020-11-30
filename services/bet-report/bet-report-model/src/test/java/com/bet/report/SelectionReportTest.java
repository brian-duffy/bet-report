package com.bet.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;

public class SelectionReportTest {

  @Test
  public void whenIncrementingExpectSuccessful() {
    SelectionReport selectionReport = SelectionReport.builder().numberOfBets(1).build();
    selectionReport.increaseNumberOfBets();
    assertThat(selectionReport.getNumberOfBets()).isEqualTo(2);
  }

  @Test
  public void whenIncreasingStakeExpectCorrectValueAndScale() {
    SelectionReport selectionReport = SelectionReport.builder().totalStakes(new BigDecimal("1.5"))
        .build();
    selectionReport.increaseStake(new BigDecimal("1.0"));
    assertThat(selectionReport.getTotalStakes()).isEqualTo("2.50");
  }

  @Test
  public void whenIncreasingTotalLiabilityExpectCorrectValue() {
    SelectionReport selectionReport = SelectionReport.builder().totalLiability(new BigDecimal("10"))
        .build();
    Bet bet = Bet.builder().price(new BigDecimal("1.00")).stake(new BigDecimal("2.00")).build();
    selectionReport.increaseTotalLiability(bet);
    assertThat(selectionReport.getTotalLiability()).isEqualTo(new BigDecimal("12.00"));
  }
}
