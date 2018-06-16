package com.weber.okex.ticker.client.fcoin.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.weber.okex.ticker.client.fcoin.FCoinRestClient;
import com.weber.okex.ticker.client.fcoin.constants.FCoinApiConstants;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.model.FCoinSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.weber.okex.ticker.client.fcoin.impl.FCoinApiServiceGenerator.createService;
import static com.weber.okex.ticker.client.fcoin.impl.FCoinApiServiceGenerator.executeSync;


/**
 * Implementation of REST API using Retrofit with synchronous/blocking method calls.
 */
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
    String method = "GET";
    String url = FCoinApiConstants.API_BASE_URL +  "/v2/market/ticker/" + symbol;
    String timestamp = System.currentTimeMillis() + "";
    Map<String, String> params = new HashMap<>();
    params.put("symbol", symbol);
    createService(FCoinApiService.class, apiKey, secretKey, method, url, timestamp, params);
    return executeSync(fCoinApiService.ticker(symbol)).getData();
  }
}
