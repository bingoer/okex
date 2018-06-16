package com.weber.okex.ticker.client;

import java.util.Collection;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.client.coinmarketcap.exception.CmcApiException;
import com.weber.okex.ticker.client.fcoin.FCoinRestClient;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.client.fcoin.impl.FCoinApiService;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;
import com.weber.okex.ticker.model.FCoinSymbol;
import com.weber.okex.ticker.service.CmcSymbolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FCoinRestClientTest {

  @Autowired private FCoinRestClient fCoinRestClient;
  @Test
  public void currencies() {
    Collection<String> currencies = fCoinRestClient.currencies();
    System.out.println(currencies);
  }

  @Test
  public void symboles() {
    Collection<FCoinSymbol> symboles = fCoinRestClient.symboles();
    System.out.println(symboles);
  }

  @Test
  public void ticker() {
    String symbol = "ftusdt";
    FCoinTickerWarpper fCoinTickerWarpper = fCoinRestClient.ticker(symbol);
    System.out.println(fCoinTickerWarpper);
  }

}
