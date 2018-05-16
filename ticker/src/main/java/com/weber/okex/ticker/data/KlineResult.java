package com.weber.okex.ticker.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KlineResult {
  private static int SUCCESS = 1;
  private static int FAIL = 0;
  private int success;
  private String symbol;

  public KlineResult(int success) {
    this.success = success;
  }

  public static KlineResult buildSuccess() {
    return new KlineResult(SUCCESS);
  }

  public static KlineResult buildFail() {
    return new KlineResult(FAIL);
  }

  public boolean isSuccess(){
    return success == SUCCESS;
  }

  public boolean isFail(){
    return success == FAIL;
  }
}
