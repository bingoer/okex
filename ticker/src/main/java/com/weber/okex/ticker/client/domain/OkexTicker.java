package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OkexTicker {
  private BigDecimal buy;
  private BigDecimal high;
  private BigDecimal last;
  private BigDecimal low;
  private BigDecimal sell;
  private BigDecimal vol;
}
