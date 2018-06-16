package com.weber.okex.ticker.client.fcoin.domain;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.model.CmcTicker;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinCurencyWarpper {

    private int status;

    private Collection<String> data;
}
