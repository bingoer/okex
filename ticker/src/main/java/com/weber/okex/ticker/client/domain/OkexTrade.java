package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkexTrade {

  private Long tid;
  private Long date;
  @JsonProperty("date_ms")
  private Long dateMs;
  private BigDecimal price;
  private BigDecimal amount;
  private String type;
}
