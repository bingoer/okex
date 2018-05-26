package com.weber.okex.ticker.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
   * 获取OKEx币币行情
   *
   * @param symbol
   * @return
   */
  @GET("/api/v1/depth.do")
  Call<OkexDepthWarpper> depth(@Query("symbol") String symbol, @Query("size") Integer size);

  /**
   * 获取OKEx币币交易信息(600条)
   *
   * @param symbol
   * @param since
   * @return
   */
  @GET("/api/v1/trades.do")
  Call<List<OkexTrade>> trades(@Query("symbol") String symbol, @Query("since") Long since);

  /**
   * 获取OKEx币币K线数据(每个周期数据条数2000左右)
   *
   * @param symbol
   * @param type 1min/3min/5min/15min/30min/1day/3day/1week/1hour/2hour/4hour/6hour/12hour
   * @param
   * @return
   */
  @GET("/api/v1/kline.do")
  Call<List<BigDecimal[]>> kline(
      @Query("symbol") String symbol,
      @Query("type") String type,
      @Query("size") Integer size,
      @Query("since") Long since);


  /**
   * 获取用户信息
   *
   * @param
   * @return
   */
  @POST("/api/v1/userinfo.do")
  Call<Object> userInfo();
}
