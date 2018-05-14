package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OkexDepthWarpper {

  private List<BigDecimal[]> asks = new ArrayList<>();

  private List<BigDecimal[]> bids = new ArrayList<>();

}
