package com.weber.okex.ticker.controller;

import java.math.BigDecimal;

import com.weber.okex.ticker.cache.BuyTradesCache;
import com.weber.okex.ticker.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

  @Value("${okex.threshold.amount}")
  private BigDecimal THRESHOLD_AMOUNT;

  @Autowired
  private TickerService tickerService;

  @GetMapping(value = "/hot_symbols")
  public Object getHotSymbolsByAmount(){
    return tickerService.getHotSymbolsByAmount(THRESHOLD_AMOUNT);
  }

  @GetMapping(value = "/buy_trades")
  public Object dashboard(){
    return BuyTradesCache.getAll();
  }


}
