package com.bet.report;

import com.bet.report.enums.Currency;
import com.bet.report.enums.Selection;
import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Service one model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bet {

  @NotNull
  @Trim
  @Parsed(index = 0)
  private String betId;

  @NotNull
  @Parsed(index = 1)
  private String betTimestamp;

  @NotNull
  @Parsed(index = 2)
  private int selectionId;

  @NotNull
  @Parsed(index = 3)
  @EnumOptions(customElement = "description")
  private Selection selectionName;

  @NotNull
  @Parsed(index = 4)
  private BigDecimal stake;

  @NotNull
  @Parsed(index = 5)
  private BigDecimal price;

  @NotNull
  @Trim
  @Parsed(index = 6)
  @EnumOptions(customElement = "code")
  private Currency currency;
}
