package com.weber.okex.ticker.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.client.domain.OkexTicker;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {
    private Long id;
    private String symbol;
    private BigDecimal buy;
    private BigDecimal high;
    private BigDecimal last;
    private BigDecimal low;
    private BigDecimal sell;
    private BigDecimal vol;
    private BigDecimal amount;
    private Date createTime;
    private Date updateTime;

    public Ticker(OkexTickerWarpper tickerWarpper) {
      this.setSymbol(tickerWarpper.getSymbol());
      this.setUpdateTime(tickerWarpper.getDate() != null ? new Date(tickerWarpper.getDate() * 1000) : null);

      OkexTicker ticker = tickerWarpper.getTicker();
      if (ticker != null) {
        this.buy = ticker.getBuy();
        this.high = ticker.getHigh();
        this.last = ticker.getLast();
        this.low = ticker.getLow();
        this.sell = ticker.getSell();
        this.vol = ticker.getVol();
        this.amount = ticker.getLast().multiply(ticker.getVol());
      }
    }
}