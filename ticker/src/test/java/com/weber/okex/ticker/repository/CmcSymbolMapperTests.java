package com.weber.okex.ticker.repository;

import com.weber.okex.ticker.model.CmcSymbol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.weber.okex.ticker.repository")//将项目中对应的mapper类的路径加进来就可以了
public class CmcSymbolMapperTests {

  @Autowired
  private CmcSymbolMapper cmcSymbolMapper;

  @Test
  public void contextLoads() {}

  @Test
  public void insert(){
    CmcSymbol cmcSymbol = new CmcSymbol();
    cmcSymbol.setName("Bitcoin");
    cmcSymbol.setSymbol("BTC");
    cmcSymbol.setWebsiteSlug("bitcoin");
    cmcSymbolMapper.insert(cmcSymbol);
  }
}
