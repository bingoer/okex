package com.weber.okex.ticker.strategy;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.data.KlineResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PriceStrategy1Min<T> extends AbstratStractegy<KlineResult> {

  @Value("${okex.kline.size}")
  private Integer klineSize;

  String symbol;

  List<OkexKline> klines;

  public PriceStrategy1Min<T> build(String symbol, List<OkexKline> klines) {
    this.symbol = symbol;
    this.klines = klines;
    return this;
  }

  @Override
  public KlineResult execute() {
    String msg = "";
    OkexKline first = klines.get(0);
    OkexKline secend = klines.get(1);
    if (first.getClose().divide(first.getOpen(), 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("1.02")) >= 0) {
      msg += MessageFormat.format("1分钟内价格涨了2%以上[after:{0},before:{1}]-",
          first.getClose().toPlainString(), first.getOpen().toPlainString());
      if (secend.getClose().subtract(secend.getOpen()).compareTo(BigDecimal.ZERO) >= 0) {
        msg += MessageFormat.format("1分钟前价格也是上涨[after:{0},before:{1}]-",
            secend.getClose().toPlainString(), secend.getOpen().toPlainString());
      } else {
        return KlineResult.buildFailWithSymbol(symbol, null);
      }
    } else {
      return KlineResult.buildFailWithSymbol(symbol, null);
    }

    int evaluateCount = klines.size() > klineSize ? klineSize : klines.size();
    int downPriceCount = 0;
    for (int i = 0; i < evaluateCount; i++) {
      if (klines.get(i).getClose().subtract(klines.get(i).getOpen()).compareTo(BigDecimal.ZERO) < 0) {
        if ((++downPriceCount)/(klines.size()*1.0) > 0.3) {
          return KlineResult.buildFailWithSymbol(symbol, null);
        }
      }
    }

    return KlineResult.buildSuccess(msg + MessageFormat.format("最近{0}根k线70%都是持平或上涨", klineSize));
  }
}
