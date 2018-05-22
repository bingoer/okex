package com.weber.okex.ticker.service.impl;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;
import com.weber.okex.ticker.repository.CmcSymbolMapper;
import com.weber.okex.ticker.service.CmcSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmcSymbolServiceImpl implements CmcSymbolService {

  @Autowired
  private CmcSymbolMapper cmcSymbolMapper;

  @Override
  public int save(CmcSymbol symbol) {
    return cmcSymbolMapper.insertSelective(symbol);
  }

  @Override
  public int save(Collection<CmcSymbol> symbols) {
    return cmcSymbolMapper.insertSymbols(symbols);
  }

  @Override
  public int emptyAndsave(Collection<CmcSymbol> symbols) {
    cmcSymbolMapper.truncate();
    return cmcSymbolMapper.insertSymbols(symbols);
  }

  @Override
  public CmcSymbol getSymbol(String symbol) {
    return cmcSymbolMapper.selectBySymbol(symbol);
  }

  @Override
  public Collection<CmcSymbol> getSymbols() {
    return cmcSymbolMapper.selectAll();
  }
}
