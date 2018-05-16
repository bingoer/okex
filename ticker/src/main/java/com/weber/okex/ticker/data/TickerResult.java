package com.weber.okex.ticker.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TickerResult {
  private static int SUCCESS = 1;
  private static int FAIL = 0;
  private int success;
  private String symbol;

  public TickerResult(int success) {
    this.success = success;
  }

  public static TickerResult buildSuccess() {
    return new TickerResult(SUCCESS);
  }

  public static TickerResult buildFail() {
    return new TickerResult(FAIL);
  }

  public boolean isSuccess(){
    return success == SUCCESS;
  }

  public boolean isFail(){
    return success == FAIL;
  }
}
