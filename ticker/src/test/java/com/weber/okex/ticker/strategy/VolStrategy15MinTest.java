package com.weber.okex.ticker.strategy;

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

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VolStrategy15MinTest {

  @Value("#{'${okex.symbols.usdt}'.split(',')}")
  private List<String> symbols;
  @Value("${okex.test.period.15min}")
  private String periodType;

  @Autowired
  OkexClient okexClient;

  @Autowired
  VolStrategy15Min<KlineResult> volStrategy15Min;

  @Test
  public void execute() {
    symbols.forEach( symbol -> {
      try {
        List<OkexKline> klines = okexClient.kline(symbol, periodType, null, null);
        System.out.println(volStrategy15Min.build(symbol, klines).execute());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (HttpException e) {
        e.printStackTrace();
      }
    });
  }
}
