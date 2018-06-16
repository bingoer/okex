package com.weber.okex.ticker.client.fcoin.impl;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinCurencyWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinDomain;
import com.weber.okex.ticker.client.fcoin.domain.FCoinSymbolWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/** Binance's REST API URL mappings and endpoint security configuration. */
public interface FCoinApiService {

  // General endpoints
  @GET("/v2/public/currencies")
  Call<FCoinCurencyWarpper> currencies();


  @GET("/v2/public/symbols")
  Call<FCoinSymbolWarpper> symbols();

  /**
   * 获取行情
   *
   * @param symbol
   * @return
   */
  @GET("/v2/market/ticker/{symbol}")
  Call<FCoinDomain<FCoinTickerWarpper>> ticker(@Path("symbol") String symbol);

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
