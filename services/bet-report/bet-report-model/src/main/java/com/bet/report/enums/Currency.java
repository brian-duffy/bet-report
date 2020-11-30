package com.bet.report.enums;

/**
 * The enum Currency.
 */
public enum Currency {
  /**
   * Gbp currency.
   */
  GBP("GBP"),
  /**
   * Eur currency.
   */
  EUR("EUR");

  /**
   * The Code.
   */
  public final String code;

  Currency(String code) {
    this.code = code;
  }
}
