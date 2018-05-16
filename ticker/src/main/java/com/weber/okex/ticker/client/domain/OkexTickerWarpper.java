package com.weber.okex.ticker.client.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkexTickerWarpper {
  private String symbol;
  private Long date;
  private OkexTicker ticker;
}
