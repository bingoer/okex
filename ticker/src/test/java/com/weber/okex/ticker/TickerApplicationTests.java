package com.weber.okex.ticker;

import com.weber.okex.ticker.client.OkexRestClient;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.impl.OkexOkexRestClientImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TickerApplicationTests {

  private static final String apiKey = "16e8a0dd-78ca-47c7-b52f-f153264f0055";
  private static final String secretKey = "7C84B3A8CD262AFC61D2D8F0546BD491";
  private OkexRestClient okexRestClient = new OkexOkexRestClientImpl(null, null);

  @Test
  public void contextLoads() {}

  @Test
  public void ticker(){
    OkexTickerWarpper okexTickerWarpper = okexRestClient.ticker("okb_usdt");
    System.out.println(okexTickerWarpper);
  }
}
