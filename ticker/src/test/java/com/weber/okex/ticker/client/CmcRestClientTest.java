package com.weber.okex.ticker.client;

import java.util.Collection;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.model.CmcSymbol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmcRestClientTest {

  @Autowired
  private CmcRestClient cmcRestClient;

  @Test
  public void listings(){
    Collection<CmcSymbol> symbols = cmcRestClient.listings();
    System.out.println(symbols);
  }
}
