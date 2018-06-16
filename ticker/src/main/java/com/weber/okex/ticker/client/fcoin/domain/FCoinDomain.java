package com.weber.okex.ticker.client.fcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinDomain<T> {
  private int status;

  private String msg;

  private T data;
}
