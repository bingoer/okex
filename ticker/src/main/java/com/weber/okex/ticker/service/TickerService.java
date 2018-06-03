package com.weber.okex.ticker.service;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.model.Ticker;

public interface TickerService {

  int saveOrUpdate(Ticker ticker);

  List<Ticker> getTickers();

  List<String> getHotSymbolsByAmount(BigDecimal amount);
}
