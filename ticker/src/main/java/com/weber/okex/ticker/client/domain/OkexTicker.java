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

  public static void main(String[] args) {
//    BigDecimal last = new BigDecimal(0.00018635);
    BigDecimal last = new BigDecimal(0.00014635);
    BigDecimal vol = new BigDecimal(3578416.68026496);
    System.out.println(last.multiply(vol));
  }
}
