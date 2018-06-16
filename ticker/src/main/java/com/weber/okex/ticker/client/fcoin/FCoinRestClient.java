package com.weber.okex.ticker.client.fcoin;

import java.util.Collection;

import com.weber.okex.ticker.client.fcoin.domain.FCoinDomain;
import com.weber.okex.ticker.client.fcoin.domain.FCoinTickerWarpper;
import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;
import com.weber.okex.ticker.model.FCoinSymbol;

public interface FCoinRestClient {

  Collection<String> currencies();

  Collection<FCoinSymbol> symboles();

  FCoinTickerWarpper ticker(String symbol);
}
