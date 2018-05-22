package com.weber.okex.ticker.client.coinmarketcap.domain;

import com.weber.okex.ticker.client.constants.OkexApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * API error object.
 */
public class CmcApiError {

  /**
   * Error code.
   */
  private int code;

  /**
   * Error message.
   */
  private String msg;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, OkexApiConstants.TO_STRING_BUILDER_STYLE)
        .append("code", code)
        .append("msg", msg)
        .toString();
  }
}
