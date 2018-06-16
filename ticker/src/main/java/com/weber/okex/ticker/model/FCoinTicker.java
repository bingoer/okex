package com.weber.okex.ticker.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCoinTicker {

  private String symbol;
  private BigDecimal lastPrice;
  private BigDecimal lastVol;
  private BigDecimal buy;
  private BigDecimal buyVol;
  private BigDecimal sell;
  private BigDecimal sellVol;
  private BigDecimal pre24HPrice;
  private BigDecimal high;
  private BigDecimal low;
}
