package com.weber.okex.ticker.client.fcoin.domain;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.model.FCoinSymbol;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinSymbolWarpper {

    private int status;

    private Collection<FCoinSymbol> data;
}
