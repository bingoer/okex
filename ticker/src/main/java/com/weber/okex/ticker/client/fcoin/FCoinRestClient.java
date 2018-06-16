package com.weber.okex.ticker.client.fcoin;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.weber.okex.ticker.client.fcoin.domain.FCoinDomain;
import com.weber.okex.ticker.client.fcoin.domain.FCoinOrderWarpper;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;
import com.weber.okex.ticker.model.FCoinSymbol;
import retrofit2.Call;
import retrofit2.http.Path;

public interface FCoinRestClient {

  Collection<String> currencies();

  Collection<FCoinSymbol> symboles();

  FCoinTickerWarpper ticker(String symbol);

  String createOrder(LinkedHashMap<String, String> params);

  Collection<FCoinOrderWarpper> orders(LinkedHashMap<String, String> params);

  Boolean cancelOrder(String orderId);
}
