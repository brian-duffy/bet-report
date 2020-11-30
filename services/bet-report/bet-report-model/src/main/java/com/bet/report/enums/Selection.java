package com.bet.report.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The enum Selections.
 */
public enum Selection {

  /**
   * The My fair lady.
   */
  @JsonProperty("My Fair Lady")
  MY_FAIR_LADY("My Fair Lady"),
  /**
   * The Always a runner.
   */
  @JsonProperty("Always a Runner")
  ALWAYS_A_RUNNER("Always a Runner"),
  /**
   * The Bilbos adventure.
   */
  @JsonProperty("Bilbo's Adventure")
  BILBOS_ADVENTURE("Bilbo's Adventure"),
  /**
   * The Fan the flames.
   */
  @JsonProperty("Fan the Flames")
  FAN_THE_FLAMES("Fan the Flames");

  /**
   * The Code.
   */
  public final String description;

  Selection(String description) {
    this.description = description;
  }
}
