package com.weber.okex.ticker.client.coinmarketcap;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.model.CmcTicker;

public interface CmcRestClient {

  Collection<CmcSymbol> listings();

  Collection<CmcTicker> ticker(int start, int limit, String sort);
}
