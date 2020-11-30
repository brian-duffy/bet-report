package com.bet.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.univocity.parsers.annotations.Headers;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Currency report.
 */
@ApiResponse
@Getter
@Setter
@SuperBuilder
@Headers
public class CurrencyReport extends Report {

  @JsonProperty("No Of Bets")
  private int numberOfBets;

  /**
   * Instantiates a new Currency report.
   *
   * @param bet the bet
   */
  public CurrencyReport(Bet bet) {
    super();
    numberOfBets++;
    setTotalStakes(bet.getStake());
    setCurrency(bet.getCurrency());
    increaseTotalLiability(bet);
  }

  /**
   * Increase number of bets.
   */
  public void increaseNumberOfBets() {
    numberOfBets++;
  }
}
