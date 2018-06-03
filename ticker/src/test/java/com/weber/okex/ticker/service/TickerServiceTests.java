package com.weber.okex.ticker.service;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.Ticker;
import com.weber.okex.ticker.repository.CmcSymbolMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.weber.okex.ticker.repository")//将项目中对应的mapper类的路径加进来就可以了
public class TickerServiceTests {

  @Value("${okex.threshold.amount}")
  private BigDecimal THRESHOLD_AMOUNT;

  @Autowired
  private TickerService tickerService;

  @Test
  public void insert(){
    Ticker ticker = new Ticker();
    ticker.setSymbol("btc");
    ticker.setHigh(new BigDecimal("10.10"));
    tickerService.saveOrUpdate(ticker);
  }

  @Test
  public void getTickers(){
    List<Ticker> tickers = tickerService.getTickers();
    System.out.println(tickers);
  }
  @Test
  public void getHotSymbolsByAmount(){
    List<String> symbols = tickerService.getHotSymbolsByAmount(THRESHOLD_AMOUNT);
    System.out.println(symbols);
  }
}
