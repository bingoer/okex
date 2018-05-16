package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class OkexKline {
  @Getter @Setter private Long dateMs;
  @Getter @Setter private BigDecimal open;
  @Getter @Setter private BigDecimal high;
  @Getter @Setter private BigDecimal low;
  @Getter @Setter private BigDecimal close;
  @Getter @Setter private BigDecimal vol;
  private String date;

  public OkexKline(OkexKline okexKline) {
    this.dateMs = okexKline.dateMs;
    this.open = okexKline.open;
    this.high = okexKline.high;
    this.low = okexKline.low;
    this.close = okexKline.close;
  }

  public String getDate() {
    return new Date(dateMs).toString();
  }
}
