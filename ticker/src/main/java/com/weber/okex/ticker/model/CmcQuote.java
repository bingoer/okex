package com.weber.okex.ticker.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcQuote {

  private BigDecimal price;
  /** 24小时交易量 */
  @JsonProperty("volume_24h")
  private BigDecimal volume24h;
  /** 市场总值 */
  @JsonProperty("market_cap")
  private BigDecimal marketCap;

  @JsonProperty("percent_change_1h")
  private Double percentChange1h;

  @JsonProperty("percent_change_24h")
  private Double percentChange24h;

  @JsonProperty("percent_change_7d")
  private Double percentChange7d;
}
