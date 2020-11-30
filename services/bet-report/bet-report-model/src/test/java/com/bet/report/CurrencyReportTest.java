package com.bet.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;

public class CurrencyReportTest {

  @Test
  public void whenIncrementingExpectSuccessful() {
    CurrencyReport currencyReport = CurrencyReport.builder().numberOfBets(1).build();
    currencyReport.increaseNumberOfBets();
    assertThat(currencyReport.getNumberOfBets()).isEqualTo(2);
  }

  @Test
  public void whenIncreasingStakeExpectCorrectValueAndScale() {
    CurrencyReport currencyReport = CurrencyReport.builder().totalStakes(new BigDecimal("1.5"))
        .build();
    currencyReport.increaseStake(new BigDecimal("1.0"));
    assertThat(currencyReport.getTotalStakes()).isEqualTo("2.50");
  }

  @Test
  public void whenIncreasingTotalLiabilityExpectCorrectValue() {
    CurrencyReport currencyReport = CurrencyReport.builder().totalLiability(new BigDecimal("10"))
        .build();
    Bet bet = Bet.builder().price(new BigDecimal("1.00")).stake(new BigDecimal("2.00")).build();
    currencyReport.increaseTotalLiability(bet);
    assertThat(currencyReport.getTotalLiability()).isEqualTo(new BigDecimal("12.00"));
  }
}
