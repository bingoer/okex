package com.weber.okex.ticker.client.coinmarketcap.domain;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.weber.okex.ticker.model.CmcSymbol;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcSymbolWarpper {
    Collection<CmcSymbol> data;
}
