package com.weber.okex.ticker.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weber.okex.ticker.client.domain.OkexAccountBalance;
import com.weber.okex.ticker.client.domain.OkexAccountBalanceWarpper;
import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;
import com.weber.okex.ticker.okexclient.rest.stock.IStockRestApi;
import com.weber.okex.ticker.okexclient.rest.stock.impl.StockRestApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OkexClient {

  @Value("${okex.base.url}")
  private String urlPrex;

  @Value("${okex.apikey}")
  private String apiKey;

  @Value("${okex.secretkey}")
  private String secretKey;

  /** get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息 */
  private IStockRestApi stockGet;

  /**
   * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
   * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入， 发送post请求之前，程序会做自动加密，生成签名。
   */
  private IStockRestApi stockPost;

  @PostConstruct
  private void init() {
    stockGet = new StockRestApi(urlPrex);
    stockPost = new StockRestApi(urlPrex, apiKey, secretKey);
  }

  /**
   * 现货行情
   *
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public OkexTickerWarpper ticker(String symbol) throws IOException, HttpException {
    String resultStr = stockGet.ticker(symbol);
    OkexTickerWarpper okexTickerWarpper = JSON.parseObject(resultStr, OkexTickerWarpper.class);
    okexTickerWarpper.setSymbol(symbol);
    return okexTickerWarpper;
  }

  /**
   * 现货市场深度
   *
   * @param symbol
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public OkexDepthWarpper depth(String symbol, String size) throws IOException, HttpException {
    String resultStr = stockGet.depth(symbol, size);
    OkexDepthWarpper depth = JSON.parseObject(resultStr, OkexDepthWarpper.class);
    return depth;
  }

  /**
   * 现货OKCoin历史交易信息
   *
   * @param symbol
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public List<OkexTrade> trades(String symbol, String since) throws IOException, HttpException {
    String resultStr = stockGet.trades(symbol, since);
    List<OkexTrade> list = JSON.parseObject(resultStr, new TypeReference<List<OkexTrade>>(){});
    return list;
  }

  /**
   * 获取OKEx币币K线数据
   *
   * @param symbol
   * @param type
   * @param size
   * @param since
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public List<OkexKline> kline(String symbol, String type, String size, String since)
      throws IOException, HttpException {
    List<OkexKline> klines = new ArrayList<>();
    String resultStr = stockGet.kline(symbol, type, size, since);
    List<String[]> rawList = null;
    try {
      rawList = JSON.parseObject(resultStr, new TypeReference<List<String[]>>(){});
    } catch (JSONException e) {
      log.error("json string error:{}", resultStr);
      e.printStackTrace();
    }
    if (CollectionUtils.isNotEmpty(rawList)) {
      rawList.forEach(array->{
        OkexKline kline = new OkexKline();
        kline.setDateMs(Long.valueOf(array[0]));
        kline.setOpen(new BigDecimal(array[1]));
        kline.setHigh(new BigDecimal(array[2]));
        kline.setLow(new BigDecimal(array[3]));
        kline.setClose(new BigDecimal(array[4]));
        kline.setVol(new BigDecimal(array[5]));
        klines.add(kline);
      });
      Collections.reverse(klines);
    }
    return klines;
  }

  /**
   * 现货用户信息 访问频率 6次/2秒
   *
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public OkexAccountBalance userinfo() throws IOException, HttpException {
    String resultStr = stockPost.userinfo();
    OkexAccountBalanceWarpper okexAccountBalanceWarpper = JSON.parseObject(resultStr, new TypeReference<OkexAccountBalanceWarpper>(){});
    if (okexAccountBalanceWarpper.getResult()) {
      return okexAccountBalanceWarpper.getInfo().getFunds();
    }
    return null;
  }

  /**
   * 现货下单交易
   *
   * @param symbol
   * @param type
   * @param price
   * @param amount
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String trade(String symbol, String type, String price, String amount)
      throws IOException, HttpException {
    String tradeResult = stockPost.trade(symbol, type, price, amount);
    System.out.println(tradeResult);
    JSONObject tradeJSV1 = JSONObject.parseObject(tradeResult);
    return tradeJSV1.getString("order_id");
  }

  /**
   * 现货获取用户订单信息
   *
   * @param symbol
   * @param orderId
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String orderInfo(String symbol, String orderId) throws IOException, HttpException {
    return stockPost.order_info(symbol, orderId);
  }

  /**
   * 现货撤销订单
   *
   * @param symbol
   * @param orderId
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String cancelOrder(String symbol, String orderId) throws IOException, HttpException {
    return stockPost.cancel_order(symbol, orderId);
  }

  /**
   * 现货批量下单
   *
   * @param symbol
   * @param type
   * @param ordersData
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String cancelOrder(String symbol, String type, String ordersData)
      throws IOException, HttpException {
    return stockPost.batch_trade(symbol, type, ordersData);
  }

  /**
   * 批量获取用户订单
   *
   * @param symbol
   * @param type
   * @param ordersIds
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String ordersInfo(String symbol, String type, String ordersIds)
      throws IOException, HttpException {
    return stockPost.orders_info(type, symbol, ordersIds);
  }

  /**
   * 获取用户历史订单信息，只返回最近七天的信息
   *
   * @param symbol
   * @param status
   * @param currentPage
   * @param pageLength
   * @return
   * @throws IOException
   * @throws HttpException
   */
  public String orderHistory(String symbol, String status, String currentPage, String pageLength)
      throws IOException, HttpException {
    return stockPost.order_history(symbol, status, currentPage, pageLength);
  }
}
