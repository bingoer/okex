package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OkexTrade {

  private Long tid;
  private Long date;
  private Long dateMs;
  private BigDecimal price;
  private BigDecimal amount;
  private String type;
}
