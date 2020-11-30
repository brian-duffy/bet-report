package com.bet.report;

import com.bet.report.enums.Selection;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Selection report.
 */
@Getter
@Setter
@ApiResponse
@SuperBuilder
public class SelectionReport extends Report {

  @JsonProperty("Selection Name")
  private Selection name;

  @JsonProperty("Num Bets")
  private int numberOfBets;

  /**
   * Instantiates a new Selection report.
   *
   * @param bet the bet
   */
  public SelectionReport(Bet bet) {
    super();
    numberOfBets++;
    setName(bet.getSelectionName());
    setCurrency(bet.getCurrency());
    setTotalStakes(bet.getStake());
    increaseTotalLiability(bet);
  }


  /**
   * Increase number of bets.
   */
  public void increaseNumberOfBets() {
    numberOfBets++;
  }


}
