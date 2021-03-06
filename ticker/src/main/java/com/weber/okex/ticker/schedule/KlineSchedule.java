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
import com.weber.okex.ticker.strategy.PriceStrategy15Min;
import com.weber.okex.ticker.strategy.PriceStrategy1Min;
import com.weber.okex.ticker.strategy.PriceStrategy3Min;
import com.weber.okex.ticker.strategy.VolStrategy15Min;
import com.weber.okex.ticker.strategy.VolStrategy1Min;
import com.weber.okex.ticker.strategy.VolStrategy3Min;
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

  @Value("#{'${okex.symbols.usdt}'.split(',')}")
  private List<String> symbols;

  @Value("#{'${okex.period.types}'.split(',')}")
  private List<String> periodTypes;

  @Value("${okex.kline.size}")
  private String klineSize;

  private List<String> mayjorSymbols;

  @Autowired private OkexClient okexClient;

  @Autowired private StrategyService strategyService;

  @Autowired
  private VolStrategy1Min<KlineResult> volStrategy1Min;

  @Autowired
  private PriceStrategy1Min<KlineResult> priceStrategy1Min;

  @Autowired
  private VolStrategy3Min<KlineResult> volStrategy3Min;

  @Autowired
  private PriceStrategy3Min<KlineResult> priceStrategy3Min;

  @Autowired
  private VolStrategy15Min<KlineResult> volStrategy15Min;

  @Autowired
  private PriceStrategy15Min<KlineResult> priceStrategy15Min;

//  @PostConstruct
  private void init(){
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

//  @Scheduled(fixedRate = 1000)
  public void analyse() {
    mayjorSymbols.forEach(
        symbol -> {
          try {
            List<OkexKline> klines = okexClient.kline(symbol, periodTypes.get(0), klineSize, null);
            KlineResult result = volStrategy1Min.build(symbol, klines).execute();
            if (result.isSuccess()) {
              String msg = result.getMsg() + "";
              System.out.println(symbol +  msg);
              result = priceStrategy1Min.build(symbol, klines).execute();
              if (result.isSuccess()) {
                result.setMsg(msg + result.getMsg());
                System.out.println(result);
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          }
        });
  }


//  @Scheduled(fixedRate = 1000)
  public void analyse15Min() {
    mayjorSymbols.forEach(
        symbol -> {
          try {
            List<OkexKline> klines = okexClient.kline(symbol, periodTypes.get(3), klineSize, null);
            KlineResult result = volStrategy15Min.build(symbol, klines).execute();
            if (result.isSuccess()) {
              String msg = result.getMsg();
              System.out.println(symbol +  msg);
              result = priceStrategy15Min.build(symbol, klines).execute();
              if (result.isSuccess()) {
                result.setMsg(msg + result.getMsg());
                System.out.println(result);
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          }
        });
  }

//  @Scheduled(fixedRate = 1000)
  public void kline() {
    mayjorSymbols.forEach(
        symbol -> {
          try {
            List<OkexKline> list =
                okexClient.kline(symbol, periodTypes.get(0), klineSize, null);
            KlineResult klineResult = strategyService.analyseKline(list);
            if (klineResult.isSuccess()) {
              klineResult.setSymbol(symbol);
              System.out.println(symbol + " is up now! time is:" + dateFormat.format(new Date()) + "\n" + klineResult.getMsg());
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          } catch (Throwable e) {
            log.error("symbol={}, time is:{}", symbol, dateFormat.format(new Date()));
            e.printStackTrace();
          }
        });
  }
}
