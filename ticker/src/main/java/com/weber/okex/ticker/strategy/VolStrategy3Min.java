package com.weber.okex.ticker.strategy;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.data.KlineResult;
import org.springframework.stereotype.Component;

@Component
public class VolStrategy3Min<T> extends AbstratStractegy<KlineResult> {

  String symbol;
  List<OkexKline> klines;

  public VolStrategy3Min<T> build(String symbol, List<OkexKline> klines) {
    this.symbol = symbol;
    this.klines = klines;
    return this;
  }

  @Override
  public KlineResult execute() {
    String msg = "";
    OkexKline first = klines.get(0);
    if (first.getVol().compareTo(BigDecimal.ZERO) <= 0) {
      return KlineResult.buildFailWithSymbol(symbol, null);
    }
    for (int i = 1; i < klines.size(); i++) {
      if (klines.get(i).getVol().compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }
      BigDecimal times = first.getVol().divide(klines.get(i).getVol(), 2, BigDecimal.ROUND_HALF_UP);
      if (times.compareTo(new BigDecimal(3))  < 0) {
        return KlineResult.buildFailWithSymbol(symbol, null);
      }

      if (i == 1) {
        msg = MessageFormat.format("3分钟内成交量翻了{0}倍以上[after:{1},before:{2}]-",
            times.toPlainString(), first.getVol().toPlainString(), klines.get(i).getVol().toPlainString());
      } else {
        times = klines.get(i-1).getVol().divide(klines.get(i).getVol(), 2, BigDecimal.ROUND_HALF_UP);
        if (times.compareTo(new BigDecimal(0.9))  < 0) {
          return KlineResult.buildFailWithSymbol(symbol, null);
        }
      }
    }

    return KlineResult.buildSuccessWithSymbol(symbol, msg);
  }
}
