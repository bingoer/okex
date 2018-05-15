package com.weber.okex.ticker.client.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkexAccountBalanceWarpper {

  private Boolean result;

  private Info info;

  @Getter
  @Setter
  @ToString
  public static class Info {
    private OkexAccountBalance funds;
  }
}
