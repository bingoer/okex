package com.weber.okex.ticker.client;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;

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

  OkexDepthWarpper depth(String symbol, Integer size);

  List<OkexTrade> trades(String symbole, Long since);

  List<BigDecimal[]> kline(String symbol, String type, Integer size, Long since);

  Object userInfo();

}
