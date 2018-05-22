package com.weber.okex.ticker.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcTicker extends CmcSymbol {

  private Integer rank;
  /**
   * 流通量
   */
  @JsonProperty("circulating_supply")
  private BigDecimal circulatingSupply;
  /**
   * 总供给量
   */
  @JsonProperty("total_supply")
  private BigDecimal totalSupply;
  /**
   * 最大供给量
   */
  @JsonProperty("max_supply")
  private BigDecimal maxSupply;

  /**
   * 更新时间
   */
  @JsonProperty("last_updated")
  private Long lastUpdated;

  private Map<String, CmcQuote> quotes;
}
