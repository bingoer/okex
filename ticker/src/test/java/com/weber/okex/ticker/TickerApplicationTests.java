package com.weber.okex.ticker;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.OkexRestClient;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.impl.OkexRestClientImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TickerApplicationTests {

  @Value("${api.okex.apikey}")
  private String apiKey;

  @Value("${api.okex.secretkey}")
  private String secretKey;

  private OkexRestClient okexRestClient = new OkexRestClientImpl(null, null);

  private OkexRestClient okexRestClientPost = new OkexRestClientImpl(apiKey, secretKey);

  @Test
  public void contextLoads() {}

  @Test
  public void ticker(){
    OkexTickerWarpper okexTickerWarpper = okexRestClient.ticker("okb_usdt");
    System.out.println(okexTickerWarpper);
  }

  @Test
  public void kline(){
    List<BigDecimal[]> kline = okexRestClient.kline("okb_usdt", "1min", 50, null);
    kline.forEach(line -> System.out.println(line[0]));
  }

  @Test
  public void userInfo(){
    System.out.println(okexRestClientPost.userInfo());
  }
}
