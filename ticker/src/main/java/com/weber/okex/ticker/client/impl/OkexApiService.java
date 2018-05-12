package com.weber.okex.ticker.client.impl;

import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Binance's REST API URL mappings and endpoint security configuration.
 */
public interface OkexApiService {

  // General endpoints

  @GET("/api/v1")
  Call<Void> ping();

  @GET("/api/v1/ticker.do")
  Call<OkexTickerWarpper> ticker(@Query("symbol") String symbol);
}
