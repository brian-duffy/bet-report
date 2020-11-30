package com.bet.report;

import com.bet.report.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Report.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class Report {

  /**
   * The Rounding mode.
   */
  static final RoundingMode roundingMode = RoundingMode.HALF_UP;
  /**
   * The Scale.
   */
  static final int scale = 2;
  @JsonProperty("Currency")
  private Currency currency;
  @JsonProperty("Total Stakes")
  private BigDecimal totalStakes = new BigDecimal("0.00");
  @JsonProperty("Total Liability")
  private BigDecimal totalLiability = new BigDecimal("0.00");

  /**
   * Increase total liability.
   *
   * @param bet the bet
   */
  public void increaseTotalLiability(Bet bet) {
    setTotalLiability(getTotalLiability()
        .add(bet.getStake().multiply(bet.getPrice()))
        .setScale(scale, roundingMode));
  }

  /**
   * Increase stake.
   *
   * @param stake the stake
   */
  public void increaseStake(BigDecimal stake) {
    setTotalStakes(getTotalStakes().add(stake).setScale(scale, roundingMode));
  }
}
