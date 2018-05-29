package com.weber.okex.ticker.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.weber.okex.ticker.cache.BuyTradesCache;
import com.weber.okex.ticker.client.OkexClient;
import com.weber.okex.ticker.client.domain.OkexKline;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;
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

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class TradeSchedule {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Value("#{'${okex.symbols.usdt}'.split(',')}")
  private List<String> symbols;

  @Value("#{'${okex.period.types}'.split(',')}")
  private List<String> periodTypes;

  @Value("${okex.kline.size}")
  private String klineSize;

  @Value("${okex.threshold.buys-rate}")
  private double buysRate;

  @Value("${okex.threshold.distance-now}")
  private int distanceNow;

  private List<String> mayjorSymbols = symbols;

  @Autowired private OkexClient okexClient;

  @Autowired
  private StrategyService strategyService;

  @PostConstruct
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

  @Scheduled(fixedRate = 1000)
  public void analyseTrades() {
    BuyTradesCache.clearAll();
    mayjorSymbols.forEach(
        symbol -> {
          try {
            List<OkexTrade> trades = okexClient.trades(symbol,  null);
            Integer size = trades.size();
            List<OkexTrade> buys = trades.stream().filter(trade-> "buy".equals(trade.getType())).collect(toList());
            if (buys.size()/size * 1.0 > buysRate) {
              log.info(symbol + " take占比大于:{}", buysRate);
              BuyTradesCache.add(symbol, trades);

              if (System.currentTimeMillis() - trades.get(0).getDateMs() > distanceNow) {
                log.info(symbol + " time:" + trades.get(0).getDateMs() + "," + System.currentTimeMillis());
                BuyTradesCache.del(symbol);
              }
            }

          } catch (IOException e) {
            e.printStackTrace();
          } catch (HttpException e) {
            e.printStackTrace();
          }
        });
  }
}
