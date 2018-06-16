package com.weber.okex.ticker.client.fcoin.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.weber.okex.ticker.client.fcoin.FCoinRestClient;
import com.weber.okex.ticker.client.fcoin.constants.FCoinApiConstants;
import com.weber.okex.ticker.client.fcoin.domain.FCoinDomain;
import com.weber.okex.ticker.client.fcoin.domain.FCoinOrderWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.client.fcoin.exception.FCoinApiException;
import com.weber.okex.ticker.model.FCoinSymbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.weber.okex.ticker.client.fcoin.impl.FCoinApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.fcoin.impl.FCoinApiServiceGenerator.executeSync;

/** Implementation of REST API using Retrofit with synchronous/blocking method calls. */
@Slf4j
@Component
public class FCoinRestClientImpl implements FCoinRestClient {

  @Value("${api.fcoin.apikey}")
  private String apiKey;

  @Value("${api.fcoin.secretkey}")
  private String secretKey;

  private final FCoinApiService fCoinApiService;

  public FCoinRestClientImpl() {
    fCoinApiService = createService(FCoinApiService.class);
  }

  // General endpoints

  @Override
  public Collection<String> currencies() {
    return executeSync(fCoinApiService.currencies()).getData();
  }

  @Override
  public Collection<FCoinSymbol> symboles() {
    return executeSync(fCoinApiService.symbols()).getData();
  }

  @Override
  public FCoinTickerWarpper ticker(String symbol) {
    return executeSync(fCoinApiService.ticker(symbol)).getData();
  }

  @Override
  public String createOrder(LinkedHashMap<String, String> params) {
    String method = "POST";
    String url = FCoinApiConstants.API_BASE_URL + "/v2/orders";
    String timestamp = System.currentTimeMillis() + "";
    FCoinApiService apiService =
        createService(FCoinApiService.class, apiKey, secretKey, method, url, timestamp, params);
    FCoinDomain<String> result = executeSync(apiService.createOrder(params));
    if (result.getStatus() == 0) {
      return result.getData();
    } else {
      log.error("create order error.return result: {}", result);
      throw new FCoinApiException(result.getMsg());
    }
  }

  @Override
  public Collection<FCoinOrderWarpper> orders(LinkedHashMap<String, String> params) {
    String method = "GET";
    String url = FCoinApiConstants.API_BASE_URL + "/v2/orders?states=" + params.get("states") + "&symbol=" + params.get("symbol");
    String timestamp = System.currentTimeMillis() + "";
    FCoinApiService apiService =
        createService(FCoinApiService.class, apiKey, secretKey, method, url, timestamp, params);
    FCoinDomain<Collection<FCoinOrderWarpper>> result = executeSync(apiService.ordes());
    if (result.getStatus() == 0) {
      return result.getData();
    } else {
      log.error("create order error.return result: {}", result);
      throw new FCoinApiException(result.getMsg());
    }
  }

  @Override
  public Boolean cancelOrder(String orderId) {
    String method = "POST";
    String url = FCoinApiConstants.API_BASE_URL + MessageFormat.format("/v2/orders/{0}/submit-cancel", orderId);
    String timestamp = System.currentTimeMillis() + "";
    FCoinApiService apiService =
        createService(FCoinApiService.class, apiKey, secretKey, method, url, timestamp, new LinkedHashMap());
    FCoinDomain<Boolean> result = executeSync(apiService.cancelOrder(orderId));
    if (result.getStatus() == 0) {
      return result.getData();
    } else {
      log.error("cancel order error.return result: {}", result);
      throw new FCoinApiException(result.getStatus() + " : " + result.getMsg());
    }
  }
}
