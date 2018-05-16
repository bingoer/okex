package com.weber.okex.ticker.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
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
    String msg = "";
    KlineResult klineResult;

    klineResult = evaluateKlineVol(klines);
    if (klineResult.isFail()) {
      return klineResult;
    }
    msg += klineResult.getMsg();
    klineResult = evaluateKlinePrice(klines);
    klineResult.setMsg(msg + klineResult.getMsg());
    return klineResult;
  }

  /**
   * 评估成交量
   * @param klines
   * @return
   */
  private KlineResult evaluateKlineVol(List<OkexKline> klines) {
    String msg = "";
    OkexKline first = klines.get(0);
    OkexKline secend = klines.get(1);
    OkexKline third = klines.get(2);
    if (first.getVol().compareTo(BigDecimal.ZERO) <= 0
        || secend.getVol().compareTo(BigDecimal.ZERO) <= 0) {
      return KlineResult.buildFail();
    }
    BigDecimal times = first.getVol().divide(secend.getVol(), 2, BigDecimal.ROUND_HALF_UP);
    if (times.compareTo(new BigDecimal(3))  >= 0) {
      msg += MessageFormat.format("1分钟内成交量翻了{0}倍以上[{1},{2}]", times.toPlainString(), first.getVol(), secend.getVol());
      if (secend.getVol().subtract(third.getVol()).compareTo(BigDecimal.ZERO) >= 0) {
        return KlineResult.buildSuccess(msg);
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
    String msg = "";
    OkexKline first = klines.get(0);
    OkexKline secend = klines.get(1);
    OkexKline third = klines.get(2);
    if (first.getClose().divide(first.getOpen(), 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("1.01")) >= 0) {
      msg += MessageFormat.format("1分钟内价格涨了2%以上[{0},{1}]", first.getClose(), first.getOpen());
      if (secend.getClose().subtract(secend.getOpen()).compareTo(BigDecimal.ZERO) >= 0) {
        msg += MessageFormat.format("1分钟前价格也是上涨[{0},{1}]", secend.getClose(), secend.getOpen());
      } else {
        return KlineResult.buildFail();
      }
      /*if (third.getClose().subtract(third.getOpen()).compareTo(BigDecimal.ZERO) >= 0) {
        msg += MessageFormat.format("2分钟前价格也是上涨[{0},{1}]", third.getClose(), third.getOpen());
      } else {
        return KlineResult.buildFail();
      }*/
    } else {
      return KlineResult.buildFail();
    }

    int evaluateCount = klines.size() > klineSize ? klineSize : klines.size();
    int downPriceCount = 0;
    for (int i = 0; i < evaluateCount; i++) {
      if (klines.get(i).getClose().subtract(klines.get(i).getOpen()).compareTo(BigDecimal.ZERO) < 0) {
        if ((++downPriceCount)/(klines.size()*1.0) > 0.3) {
          return KlineResult.buildFail();
        }
      }
    }

    return KlineResult.buildSuccess(msg);
  }
}
