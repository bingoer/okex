package com.weber.okex.ticker.client.coinmarketcap.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.weber.okex.ticker.client.coinmarketcap.CmcRestClient;
import com.weber.okex.ticker.client.coinmarketcap.domain.CmcTickerWarpper;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;
import org.apache.commons.collections4.MapUtils;
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

  @Override
  public Collection<CmcTicker> ticker(int start, int limit, String sort) {
    CmcTickerWarpper cmcTickerWarpper = executeSync(cmcApiService.ticker(start, limit, sort));
    if (MapUtils.isNotEmpty(cmcTickerWarpper.getData())) {
      return cmcTickerWarpper.getData().values();
    }

    return null;
  }
}
