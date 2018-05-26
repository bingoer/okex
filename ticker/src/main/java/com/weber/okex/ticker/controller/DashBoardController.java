package com.weber.okex.ticker.controller;

import com.weber.okex.ticker.cache.BuyTradesCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

  @GetMapping(value = "/buy_trades")
  public Object dashboard(){
    return BuyTradesCache.getAll();
  }
}
