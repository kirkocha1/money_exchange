package com.kirill.kochnev.exchange.data.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public enum ToolType {

    @SerializedName("EURUSD")
    EURUSD("EUR/USD"),

    @SerializedName("EURGBP")
    EURGBP("EUR/GBP"),

    @SerializedName("USDJPY")
    USDJPY("USD/JPY"),

    @SerializedName("GBPUSD")
    GBPUSD("GBP/USD"),

    @SerializedName("USDCHF")
    USDCHF("USD/CHF"),

    @SerializedName("USDCAD")
    USDCAD("USD/CAD"),

    @SerializedName("AUDUSD")
    AUDUSD("AUD/USD"),

    @SerializedName("EURJPY")
    EURJPY("EUR/JPY"),

    @SerializedName("EURCHF")
    EURCHF("EUR/CHF");

    private String slashName;

    public void setSlashName(String slashName) {
        this.slashName = slashName;
    }

    ToolType(String slashName) {
        this.slashName = slashName;
    }
}
