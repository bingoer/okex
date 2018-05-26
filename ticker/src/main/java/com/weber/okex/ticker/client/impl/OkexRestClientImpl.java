package com.weber.okex.ticker.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.OkexRestClient;
import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;

import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.impl.OkexApiServiceGenerator.executeSync;

/**
 * Implementation of REST API using Retrofit with synchronous/blocking method calls.
 */
public class OkexRestClientImpl implements OkexRestClient {

  private final OkexApiService okexApiService;

  public OkexRestClientImpl(String apiKey, String secret) {
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
  public OkexDepthWarpper depth(String symbol, Integer size) {
    return executeSync(okexApiService.depth(symbol, size));
  }

  @Override
  public List<OkexTrade> trades(String symbol, Long since) {
    return executeSync(okexApiService.trades(symbol, since));
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
