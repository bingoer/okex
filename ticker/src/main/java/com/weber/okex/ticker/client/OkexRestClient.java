package com.weber.okex.ticker.client;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexTickerWarpper;

/**
 * Binance API façade, supporting synchronous/blocking access Binance's REST API.
 */
public interface OkexRestClient {

  // General endpoints

  /**
   * Test connectivity to the Rest API.
   */
  void ping();

  OkexTickerWarpper ticker(String symbol);


  List<BigDecimal[]> kline(String symbol, String type, Integer size, Long since);

  Object userInfo();

}
