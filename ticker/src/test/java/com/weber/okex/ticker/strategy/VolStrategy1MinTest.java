package com.weber.okex.ticker.strategy;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.weber.okex.ticker.client.OkexClient;
import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.data.KlineResult;
import org.apache.http.HttpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VolStrategy1MinTest {

  @Value("#{'${okex.symbols.usdt}'.split(',')}")
  private List<String> symbols;
  @Value("${okex.test.period.type}")
  private String periodType;

  @Autowired
  OkexClient okexClient;

  @Test
  public void execute() {
    symbols.forEach( symbol -> {
      try {
        List<OkexKline> klines = okexClient.kline(symbol, periodType, null, null);
        AbstratStractegy<KlineResult> stractegy = new VolStrategy1Min<>().build(symbol, klines);
        System.out.println(stractegy.execute());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (HttpException e) {
        e.printStackTrace();
      }
    });
  }
}
