package com.bet.report;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Bet report request.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class BetReportRequest {

  @Size(min = 1)
  @NotEmpty
  private List<@Valid Bet> bets;
}
