package com.weber.okex.ticker.client.fcoin.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinTickerWarpper {

    private String type;

    private Long seq;

    private BigDecimal[] ticker;
}
