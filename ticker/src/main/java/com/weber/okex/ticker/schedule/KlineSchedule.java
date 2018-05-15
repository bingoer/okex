package com.weber.okex.ticker.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.weber.okex.ticker.client.OkexClient;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KlineSchedule {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Value("#{'${okex.symbols}'.split('-')}")
  private List<String> symbols;
  @Value("#{'${okex.period.types}'.split('-')}")
  private List<String> periodTypes;


  @Autowired
  private OkexClient okexClient;

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    System.out.println("现在时间：" + dateFormat.format(new Date()));
  }

  @Scheduled(fixedRate = 5000)
  public void kline() {
    try {
      System.out.println(okexClient.kline("okb_usdt", periodTypes.get(0), null, null));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (HttpException e) {
      e.printStackTrace();
    }
  }
}
