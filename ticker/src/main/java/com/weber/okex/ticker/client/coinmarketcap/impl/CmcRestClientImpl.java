package com.weber.okex.ticker.client.coinmarketcap.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.OkexRestClient;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;

import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.executeSync;

/**
 * Implementation of REST API using Retrofit with synchronous/blocking method calls.
 */
public class CmcRestClientImpl implements OkexRestClient {

  private final CmcApiService cmcApiService;

  public CmcRestClientImpl(String apiKey, String secret) {
    cmcApiService = createService(CmcApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping() {
    executeSync(cmcApiService.listings());
  }

  @Override
  public OkexTickerWarpper ticker(String symbol) {
    return executeSync(cmcApiService.ticker(symbol));
  }

  @Override
  public List<BigDecimal[]> kline(String symbol, String type, Integer size, Long since) {
    return executeSync(cmcApiService.kline(symbol, type, size, since));
  }

  @Override
  public Object userInfo() {
    return executeSync(cmcApiService.userInfo());
  }
}
