package com.weber.okex.ticker.client.coinmarketcap.impl;

import java.util.Collection;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.model.CmcSymbol;
import org.springframework.stereotype.Component;

import static com.weber.okex.ticker.client.coinmarketcap.impl.CmcApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.coinmarketcap.impl.CmcApiServiceGenerator.executeSync;


/**
 * Implementation of REST API using Retrofit with synchronous/blocking method calls.
 */
@Component
public class CmcRestClientImpl implements CmcRestClient {

  private final CmcApiService cmcApiService;

  public CmcRestClientImpl() {
    cmcApiService = createService(CmcApiService.class, null, null);
  }

  // General endpoints


  @Override
  public Collection<CmcSymbol> listings() {
    return executeSync(cmcApiService.listings()).getData();
  }
}
