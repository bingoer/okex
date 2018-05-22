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
  public void save(CmcSymbol symbol) {
    cmcSymbolMapper.insertSelective(symbol);
  }

  @Override
  public void save(Collection<CmcSymbol> symbols) {
    cmcSymbolMapper.insertSelectiveSymbols(symbols);
  }

  @Override
  public CmcSymbol getSymbol(String symbol) {
    return cmcSymbolMapper.selectBySymbol();
  }

  @Override
  public Collection<CmcSymbol> getSymbols() {
    return cmcSymbolMapper.selectAll();
  }
}
