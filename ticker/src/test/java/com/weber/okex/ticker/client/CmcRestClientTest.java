package com.weber.okex.ticker.client;

import java.util.Collection;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.client.coinmarketcap.exception.CmcApiException;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;
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
public class CmcRestClientTest {

  @Autowired private CmcRestClient cmcRestClient;
  @Autowired private CmcSymbolService cmcSymbolService;

  @Test
  public void listings() {
    Collection<CmcSymbol> symbols = cmcRestClient.listings();
    System.out.println(symbols);
    cmcSymbolService.emptyAndsave(symbols);
  }

  @Test
  public void ticker() throws InterruptedException {
    int start = 0;
    int limit = 100;
    String sort = "id";
    while (true) {
      try {
        Collection<CmcTicker> tickers = cmcRestClient.ticker(start, limit, sort);
        if (CollectionUtils.isEmpty(tickers)) {
          break;
        }
        System.out.println(tickers);
      } catch (CmcApiException e) {
        log.error("get cmc tricker has error.", e);
      }
      start += limit;
      Thread.sleep(20000);
    }
  }
}
