package com.weber.okex.ticker.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.weber.okex.ticker.client.fcoin.FCoinRestClient;
import com.weber.okex.ticker.client.fcoin.domain.FCoinOrderWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.client.fcoin.exception.FCoinApiException;
import com.weber.okex.ticker.model.FCoinSymbol;
import com.weber.okex.ticker.model.FCoinTicker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FCoinRestClientTest {

  private static final String SYMBOL = "ftusdt";
  private static final String AMOUNT = "50";
  private static final String SELL_PRICE = "14.54";
  private static final String BUY_PRICE = "14.53";

  @Autowired private FCoinRestClient fCoinRestClient;

  @Test
  public void currencies() {
    Collection<String> currencies = fCoinRestClient.currencies();
    System.out.println(currencies);
  }

  @Test
  public void symboles() {
    Collection<FCoinSymbol> symboles = fCoinRestClient.symboles();
    System.out.println(symboles);
  }

  @Test
  public void ticker() {
    FCoinTickerWarpper fCoinTickerWarpper = fCoinRestClient.ticker(SYMBOL);
    System.out.println(fCoinTickerWarpper);
    System.out.println(fCoinTickerWarpper.buildTicker());
  }

  @Test
  public void createBuyOrder() {
    String buyOrderId = fCoinRestClient.createOrder(getBuyParams());
    System.out.println(buyOrderId);
  }

  @Test
  public void orders() {
    LinkedHashMap<String, String> params = new LinkedHashMap<>();
    params.put("states", "submitted");
    params.put("symbol", SYMBOL);
    try {
      Collection<FCoinOrderWarpper> orders = fCoinRestClient.orders(params);
      System.out.println(orders);
    } catch (FCoinApiException e) {
      e.printStackTrace();
    }
  }

//  @Test
  public void cancelOrder() {
    Boolean cancelResult =
        fCoinRestClient.cancelOrder("ULETEknxlMjwtB_iIYBe5IzfJ-19AQ_58abdNsl8XO4=");
    System.out.println(cancelResult);
  }

  @Test
  public void createOrder() {
    while (true) {
      try {
        Collection<FCoinOrderWarpper> orders = getOrders();
        while (orders == null) {
          orders = getOrders();
        }
        orders = orders.stream().filter(order->order.getSymbol().equals(SYMBOL)).collect(Collectors.toList());
        while (orders.size() >= 2) {
          orders.forEach(order->{
            if (System.currentTimeMillis() - Long.valueOf(order.getCreatedAt()) > 180 * 1000) {
              cancelOrders();
            }
          });
          orders.clear();
          log.warn("待成交订单大于2，休眠2秒钟");
          Thread.sleep(2000);
          orders = getOrders();
          while (orders == null) {
            orders = getOrders();
          }
          orders = orders.stream().filter(order->order.getSymbol().equals(SYMBOL)).collect(Collectors.toList());
        }
        Thread.sleep(500);


        FCoinTickerWarpper fCoinTickerWarpper = fCoinRestClient.ticker(SYMBOL);
        FCoinTicker ticker = fCoinTickerWarpper.buildTicker();
        BigDecimal sellPrice = ticker.getSell();
        BigDecimal buyPrice = ticker.getBuy();
        BigDecimal middlePrice = sellPrice.add(buyPrice).divide(new BigDecimal("2"), sellPrice.stripTrailingZeros().scale(), RoundingMode.DOWN);
        String sellOrderId = fCoinRestClient.createOrder(getSellParams(middlePrice));
        System.out.println(sellOrderId);
        Thread.sleep(500);
        String buyOrderId = fCoinRestClient.createOrder(getBuyParams(buyPrice));
        System.out.println(buyOrderId);
        Thread.sleep(500);
      } catch (FCoinApiException e) {
        e.printStackTrace();
        if (e.getMessage().contains("account balance insufficient")) {
          System.exit(0);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 取消所有未成交的订单
   */
  @Test
  public void cancelOrders() {
    while (true) {
      LinkedHashMap<String, String> params = new LinkedHashMap<>();
      params.put("states", "submitted");
      params.put("symbol", SYMBOL);
      try {
        Collection<FCoinOrderWarpper> orders = fCoinRestClient.orders(params);
        if (CollectionUtils.isEmpty(orders)) {
          return;
        }
        System.out.println(orders);
        orders.forEach(
            order -> {
              Boolean cancelResult = fCoinRestClient.cancelOrder(order.getId());
              System.out.println(cancelResult);
              try {
                Thread.sleep(200);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
      } catch (FCoinApiException e) {
        e.printStackTrace();
      }
    }
  }


  private LinkedHashMap<String, String> getSellParams() {
    return getParams(AMOUNT, SELL_PRICE, "sell", SYMBOL, "limit");
  }

  private LinkedHashMap<String, String> getSellParams(BigDecimal price) {
    String sellAmount = new BigDecimal(AMOUNT).multiply(new BigDecimal("0.998")).stripTrailingZeros().toPlainString();
    return getParams(sellAmount, price.stripTrailingZeros().toPlainString(), "sell", SYMBOL, "limit");
  }

  private LinkedHashMap<String, String> getBuyParams() {
    return getParams(AMOUNT, BUY_PRICE, "buy", SYMBOL, "limit");
  }

  private LinkedHashMap<String, String> getBuyParams(BigDecimal price) {
    return getParams(AMOUNT, price.stripTrailingZeros().toPlainString(), "buy", SYMBOL, "limit");
  }

  private LinkedHashMap<String, String> getParams(String amount, String price, String side, String symbol, String type) {
    LinkedHashMap<String, String> params = new LinkedHashMap<>();
    params.put("amount", amount);
    params.put("price", price);
    params.put("side", side);
    params.put("symbol", symbol);
    params.put("type", type);
    return params;
  }

  public Collection<FCoinOrderWarpper> getOrders() {
    LinkedHashMap<String, String> params = new LinkedHashMap<>();
    params.put("states", "submitted");
    params.put("symbol", SYMBOL);
    Collection<FCoinOrderWarpper> orders = null;
    try {
      orders = fCoinRestClient.orders(params);
      System.out.println(orders);
    } catch (FCoinApiException e) {
      e.printStackTrace();
    }
    return orders;
  }
}
