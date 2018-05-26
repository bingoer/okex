package com.weber.okex.ticker.client.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class OkexAccountBalanceWarpper {

  private Boolean result;

  private Info info;

  @Data
  public static class Info {
    private OkexAccountBalance funds;
  }
}
