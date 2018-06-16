package com.weber.okex.ticker.client.fcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCoinOrderWarpper {

    private String id;

    private String symbol;

    private String type;

    private String side;

    private String price;

    private String amount;

    private String state;

    @JsonProperty("executed_value")
    private String executedValue;

    @JsonProperty("fill_fees")
    private String fillFees;

    @JsonProperty("filled_amount")
    private String filledAmount;

    @JsonProperty("created_at")
    private String createdAt;

    private String source;
}
