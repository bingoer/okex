package com.weber.okex.ticker.service;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;

public interface CmcSymbolService {

  int save(CmcSymbol symbol);

  int save(Collection<CmcSymbol> symbols);

  int emptyAndsave(Collection<CmcSymbol> symbols);

  CmcSymbol getSymbol(String symbol);

  Collection<CmcSymbol> getSymbols();
}
