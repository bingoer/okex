package com.weber.okex.ticker.client.domain;

import lombok.Data;

@Data
public class OkexTickerWarpper {
  private String symbol;
  private Long date;
  private OkexTicker ticker;
}
