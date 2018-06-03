package com.weber.okex.ticker.repository;

import java.math.BigDecimal;
import java.util.List;

import com.weber.okex.ticker.model.Ticker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TickerMapper {
    int replace(Ticker record);

    int insertSelective(Ticker record);

    Ticker selectByPrimaryKey(Long id);

    List<Ticker> selectByCondition(@Param("amount") BigDecimal amount);

    int updateByPrimaryKeySelective(Ticker record);

    int updateByPrimaryKey(Ticker record);
}