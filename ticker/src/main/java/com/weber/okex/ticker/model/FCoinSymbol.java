package com.weber.okex.ticker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinSymbol {

  @JsonProperty("name")
  private String symbol;

  @JsonProperty("base_currency")
  private String baseCurrency;

  @JsonProperty("quote_currency")
  private String quoteCurrency;

  @JsonProperty("price_decimal")
  private String priceDecimal;

  @JsonProperty("amount_decimal")
  private String amountDecimal;
}
