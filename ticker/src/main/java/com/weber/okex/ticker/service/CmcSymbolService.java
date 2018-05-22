package com.weber.okex.ticker.service;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;

public interface CmcSymbolService {

  void save(CmcSymbol symbol);

  void save(Collection<CmcSymbol> symbols);

  CmcSymbol getSymbol(String symbol);

  Collection<CmcSymbol> getSymbols();
}
