package com.weber.okex.ticker.runner;

import java.util.Collection;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.service.CmcSymbolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CmcRunner implements CommandLineRunner {

  @Autowired private CmcRestClient cmcRestClient;
  @Autowired private CmcSymbolService cmcSymbolService;

  @Override
  public void run(String... args) {
    Collection<CmcSymbol> symbols = cmcRestClient.listings();
    System.out.println(symbols);
    cmcSymbolService.emptyAndsave(symbols);
    log.info("CmcRunner Successful!");
  }
}
