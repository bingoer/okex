package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class OkexAccountBalance {

  /**
   * 借入余额
   */
  private Map<String, BigDecimal> borrow = new HashMap<>();
  /**
   * 可用余额
   */
  private Map<String, BigDecimal> free = new HashMap<>();
  /**
   * 冻结余额
   */
  private Map<String, BigDecimal> freezed = new HashMap<>();

}
