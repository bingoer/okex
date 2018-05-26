package com.weber.okex.ticker.client.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OkexDepthWarpper {

  private List<BigDecimal[]> asks = new ArrayList<>();

  private List<BigDecimal[]> bids = new ArrayList<>();
}
