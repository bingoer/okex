package com.weber.okex.ticker.client.fcoin.impl;

import java.util.Collection;
import java.util.Map;

import com.weber.okex.ticker.client.fcoin.domain.FCoinCurencyWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinDomain;
import com.weber.okex.ticker.client.fcoin.domain.FCoinOrderWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinSymbolWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
   * 创建新的订单
   *
   * @param params
   * @return
   */
  @POST("/v2/orders")
  @Headers("content-type:application/json;charset=UTF-8")
  Call<FCoinDomain<String>> createOrder(@Body Map params);


  /**
   * 查询订单列表
   *
   * @param params
   * @return
   */
  @GET("/v2/orders")
  Call<FCoinDomain<Collection<FCoinOrderWarpper>>> ordes();


  /**
   * 申请撤销订单
   *
   * @param orderId
   * @return
   */
  @POST("/v2/orders/{orderId}/submit-cancel")
  Call<FCoinDomain<Boolean>> cancelOrder(@Path("orderId") String orderId);
}
