package com.weber.okex.ticker.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.weber.okex.ticker.model.Ticker;
import com.weber.okex.ticker.repository.TickerMapper;
import com.weber.okex.ticker.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickerServiceImpl implements TickerService {

  @Autowired
  private TickerMapper tickerMapper;

  @Override
  public int saveOrUpdate(Ticker ticker) {
    return tickerMapper.replace(ticker);
  }

  @Override
  public List<Ticker> getTickers() {
    return tickerMapper.selectByCondition(null);
  }


  @Override
  public List<String> getHotSymbolsByAmount(BigDecimal amount) {
    return tickerMapper.selectByCondition(amount).stream().map(Ticker::getSymbol).collect(Collectors
        .toList());
  }
}
