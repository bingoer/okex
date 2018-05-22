package com.weber.okex.ticker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmcSymbol {
    private Long id;

    private String name;

    private String symbol;

    @JsonProperty("website_slug")
    private String websiteSlug;
}