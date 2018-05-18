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
  private String msg;

  public KlineResult(int success) {
    new KlineResult(success, null, null);
  }

  public KlineResult(int success, String symbol, String msg) {
    this.success = success;
    this.symbol = symbol;
    this.msg = msg;
  }

  public static KlineResult buildSuccess() {
    return new KlineResult(SUCCESS);
  }

  public static KlineResult buildFail() {
    return new KlineResult(FAIL);
  }

  public static KlineResult buildSuccess(String msg) {
    return new KlineResult(SUCCESS, null, msg);
  }

  public static KlineResult buildFail(String msg) {
    return new KlineResult(FAIL, null, msg);
  }

  public static KlineResult buildSuccessWithSymbol(String symbol, String msg) {
    return new KlineResult(SUCCESS, symbol, msg);
  }

  public static KlineResult buildFailWithSymbol(String symbol, String msg) {
    return new KlineResult(FAIL, symbol, msg);
  }

  public boolean isSuccess(){
    return success == SUCCESS;
  }

  public boolean isFail(){
    return success == FAIL;
  }
}
