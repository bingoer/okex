package com.weber.okex.ticker.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTicker;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.data.KlineResult;
import com.weber.okex.ticker.data.TickerResult;
import com.weber.okex.ticker.service.StrategyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StrategyServiceImpl implements StrategyService {

  @Value("${okex.symbol.vol}")
  private BigDecimal symbolVol;

  @Value("${okex.kline.size}")
  private Integer klineSize;

  @Override
  public TickerResult analyseTicker(OkexTickerWarpper tickerWarpper) {
    OkexTicker ticker = tickerWarpper.getTicker();
    if (ticker.getLast().multiply(ticker.getVol()).compareTo(symbolVol) > 0) {
      return TickerResult.buildSuccess();
    }

    return TickerResult.buildFail();
  }

  @Override
  public KlineResult analyseKline(List<OkexKline> klines) {
    KlineResult klineResult;

    klineResult = evaluateKlineVol(klines);
    if (klineResult.isFail()) {
      return klineResult;
    }
    klineResult = evaluateKlinePrice(klines);
    return klineResult;
  }

  /**
   * 评估成交量
   * @param klines
   * @return
   */
  private KlineResult evaluateKlineVol(List<OkexKline> klines) {
    OkexKline first = klines.get(0);
    OkexKline secend = klines.get(1);
    OkexKline third = klines.get(2);
    if (first.getVol().compareTo(BigDecimal.ZERO) <= 0
        || secend.getVol().compareTo(BigDecimal.ZERO) <= 0) {
      return KlineResult.buildFail();
    }
    if (first.getVol()
        .divide(secend.getVol(), 2, BigDecimal.ROUND_HALF_UP)
        .compareTo(new BigDecimal(2))
        >= 0) {
      if (secend.getVol().subtract(third.getVol()).compareTo(BigDecimal.ZERO) >= 0) {
        return KlineResult.buildSuccess();
      }
    }

    return KlineResult.buildFail();
  }

  /**
   * 评估价格走势
   * @param klines
   * @return
   */
  private KlineResult evaluateKlinePrice(List<OkexKline> klines) {
    int evaluateCount = klines.size() > klineSize ? klineSize : klines.size();
    int downPriceCount = 0;
    for (int i = 0; i < evaluateCount; i++) {
      if (klines.get(i).getClose().subtract(klines.get(i).getOpen()).compareTo(BigDecimal.ZERO) < 0) {
        if ((++downPriceCount)/(klines.size()*1.0) > 0.3) {
          return KlineResult.buildFail();
        }
      }
    }

    return KlineResult.buildSuccess();
  }
}
