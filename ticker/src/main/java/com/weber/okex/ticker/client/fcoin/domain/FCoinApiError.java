package com.weber.okex.ticker.client.fcoin.domain;

import lombok.Data;

/**
 * API error object.
 */
@Data
public class FCoinApiError {

  /**
   * Error code.
   */
  private int status;

  /**
   * Error message.
   */
  private String msg;
}
