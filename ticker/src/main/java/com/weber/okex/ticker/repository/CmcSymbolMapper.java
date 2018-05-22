package com.weber.okex.ticker.repository;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CmcSymbolMapper {
  int insert(CmcSymbol record);

  int insertSelective(CmcSymbol record);

  int insertSymbols(@Param("symbols") Collection<CmcSymbol> symbols);

  CmcSymbol selectByPrimaryKey(Long id);

  CmcSymbol selectBySymbol(@Param("symbol") String symbol);

  Collection<CmcSymbol> selectAll();

  int updateByPrimaryKeySelective(CmcSymbol record);

  int updateByPrimaryKey(CmcSymbol record);

  int truncate();
}
