package com.weber.okex.ticker.client.impl;

import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** Binance's REST API URL mappings and endpoint security configuration. */
public interface OkexApiService {

  // General endpoints

  @GET("/api/v1")
  Call<Void> ping();

  /**
   * 获取OKEx币币行情
   *
   * @param symbol
   * @return
   */
  @GET("/api/v1/ticker.do")
  Call<OkexTickerWarpper> ticker(@Query("symbol") String symbol);

  /**
   * 获取OKEx币币交易信息(600条)
   *
   * @param symbol
   * @param since
   * @return
   */
  @GET("/api/v1/trades.do")
  Call<OkexDepthWarpper> trades(@Query("symbol") String symbol, @Query("since") Long since);

  /**
   * 获取OKEx币币K线数据(每个周期数据条数2000左右)
   *
   * @param symbol
   * @param type
   * @return
   */
  @GET("/api/v1/kline.do")
  Call<OkexDepthWarpper> depth(@Query("symbol") String symbol, @Query("type") String type);
}
