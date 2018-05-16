package com.weber.okex.ticker.service;

import java.util.List;

import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.data.KlineResult;
import com.weber.okex.ticker.data.TickerResult;

public interface StrategyService {

  TickerResult analyseTicker(OkexTickerWarpper tickerWarpper);

  KlineResult analyseKline(List<OkexKline> klines);
}
