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

  private OkexRestClient okexRestClient = new OkexOkexRestClientImpl(null, null);

  @Test
  public void contextLoads() {}

  @Test
  public void ticker(){
    OkexTickerWarpper okexTickerWarpper = okexRestClient.ticker("ltc_btc");
    System.out.println(okexTickerWarpper);
  }
}
