package com.weber.okex.ticker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan("com.weber.okex.ticker.repository")//将项目中对应的mapper类的路径加进来就可以了
public class TickerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TickerApplication.class, args);
  }
}
