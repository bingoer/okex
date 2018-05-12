package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkexTicker {
  private BigDecimal buy;
  private BigDecimal high;
  private BigDecimal last;
  private BigDecimal low;
  private BigDecimal sell;
  private BigDecimal vol;
}
