package com.weber.okex.ticker.client.coinmarketcap;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;

public interface CmcRestClient {

  Collection<CmcSymbol> listings();
}
