package com.weber.okex.ticker.client;

import java.io.IOException;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import org.apache.http.HttpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OkexClientTest {

  @Value("${okex.test.symbol}")
  private String symbol;
  @Value("${okex.test.period.type}")
  private String periodType;

  @Autowired
  OkexClient okexClient;

  @Test
  public void contextLoads() {}

  @Test
  public void ticker() throws IOException, HttpException {
    System.out.println(okexClient.ticker(symbol));
  }

  @Test
  public void depth() throws IOException, HttpException {
    System.out.println(okexClient.depth(symbol));
  }

  @Test
  public void trades() throws IOException, HttpException {
    System.out.println(okexClient.trades(symbol, null));
  }

  @Test
  public void kline() throws IOException, HttpException {
    List<OkexKline> klines = okexClient.kline(symbol, periodType, null, null);
    System.out.println(klines);
  }

  @Test
  public void userInfo() throws IOException, HttpException {
    System.out.println(okexClient.userinfo());
    System.out.println(okexClient.userinfo().getFree().get("usdt"));
  }
}
