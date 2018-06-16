package com.weber.okex.ticker.client.fcoin.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.model.FCoinTicker;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinTickerWarpper {

    private String type;

    private Long seq;

    private BigDecimal[] ticker;

    public FCoinTicker buildTicker() {
        if (ticker == null) {
            throw new NullPointerException("ticker array is null");
        }
        FCoinTicker fCoinTicker = new FCoinTicker();
        fCoinTicker.setLastPrice(ticker[0]);
        fCoinTicker.setLastVol(ticker[1]);
        fCoinTicker.setBuy(ticker[2]);
        fCoinTicker.setBuyVol(ticker[3]);
        fCoinTicker.setSell(ticker[4]);
        fCoinTicker.setSellVol(ticker[5]);
        fCoinTicker.setPre24HPrice(ticker[6]);
        fCoinTicker.setHigh(ticker[7]);
        fCoinTicker.setLow(ticker[8]);
        return fCoinTicker;
    }
}
