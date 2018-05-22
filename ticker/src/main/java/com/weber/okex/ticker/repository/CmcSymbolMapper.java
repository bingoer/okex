package com.weber.okex.ticker.repository;

import java.util.Collection;

import com.weber.okex.ticker.model.CmcSymbol;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CmcSymbolMapper {
    int insert(CmcSymbol record);

    int insertSelective(CmcSymbol record);

    CmcSymbol selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmcSymbol record);

    int updateByPrimaryKey(CmcSymbol record);

    void insertSelectiveSymbols(Collection<CmcSymbol> symbols);

    CmcSymbol selectBySymbol();

    Collection<CmcSymbol> selectAll();
}