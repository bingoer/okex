package com.weber.okex.ticker.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.weber.okex.ticker.client.OkexClient;
import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.data.KlineResult;
import com.weber.okex.ticker.data.TickerResult;
import com.weber.okex.ticker.service.StrategyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KlineSchedule {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Value("#{'${okex.symbols}'.split(',')}")
  private List<String> symbols;

  @Value("#{'${okex.period.types}'.split(',')}")
  private List<String> periodTypes;

  @Value("${okex.kline.size}")
  private String klineSize;

  private List<String> mayjorSymbols;

  @Autowired private OkexClient okexClient;

  @Autowired private StrategyService strategyService;

  @PostConstruct
  private void init(){
    mayjorSymbols = symbols;
  }

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    System.out.println("现在时间：" + dateFormat.format(new Date()));
  }

  @Scheduled(fixedRate = 10000)
  public void ticker() {
    List<String> tempMayjorSymbols = new ArrayList<>();
    symbols.forEach(
        symbol -> {
          try {
            OkexTickerWarpper tickerWarpper = okexClient.ticker(symbol);
            TickerResult tickerResult = strategyService.analyseTicker(tickerWarpper);
            if (tickerResult.isSuccess()) {
              tickerResult.setSymbol(symbol);
              tempMayjorSymbols.add(symbol);
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          } catch (Throwable e) {
            log.error("symbol={}," + dateFormat.format(new Date()), symbol);
            e.printStackTrace();
          }
        });
    mayjorSymbols = tempMayjorSymbols;
    log.info("mayjorSymbols=" + mayjorSymbols);
  }

  @Scheduled(fixedRate = 1000)
  public void kline() {
    mayjorSymbols.forEach(
        symbol -> {
          try {
            List<OkexKline> list1min =
                okexClient.kline(symbol, periodTypes.get(0), klineSize, null);
            KlineResult klineResult1min = strategyService.analyseKline(list1min);
            if (klineResult1min.isSuccess()) {
              klineResult1min.setSymbol(symbol);
              System.out.println(symbol + " is success! time is:" + dateFormat.format(new Date()));
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          } catch (Throwable e) {
            log.error("symbol={}," + dateFormat.format(new Date()), symbol);
            e.printStackTrace();
          }
        });
  }
}
