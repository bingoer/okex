package com.weber.okex.ticker.client.coinmarketcap.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.model.CmcTicker;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcTickerWarpper {
    Map<String, CmcTicker> data;
}
