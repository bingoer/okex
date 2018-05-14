package com.weber.okex.ticker.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.OkexRestClient;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;

import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.executeSync;

/**
 * Implementation of REST API using Retrofit with synchronous/blocking method calls.
 */
public class OkexOkexRestClientImpl implements OkexRestClient {

  private final OkexApiService okexApiService;

  public OkexOkexRestClientImpl(String apiKey, String secret) {
    okexApiService = createService(OkexApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping() {
    executeSync(okexApiService.ping());
  }

  @Override
  public OkexTickerWarpper ticker(String symbol) {
    return executeSync(okexApiService.ticker(symbol));
  }

  @Override
  public List<BigDecimal[]> kline(String symbol, String type, Integer size, Long since) {
    return executeSync(okexApiService.kline(symbol, type, size, since));
  }

  @Override
  public Object userInfo() {
    return executeSync(okexApiService.userInfo());
  }
}
