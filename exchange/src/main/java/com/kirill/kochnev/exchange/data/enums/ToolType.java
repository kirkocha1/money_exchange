package com.kirill.kochnev.exchange.data.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public enum ToolType {

    @SerializedName("EURUSD")
    EURUSD("EUR/USD", 1),

    @SerializedName("EURGBP")
    EURGBP("EUR/GBP", 2),

    @SerializedName("USDJPY")
    USDJPY("USD/JPY", 3),

    @SerializedName("GBPUSD")
    GBPUSD("GBP/USD", 4),

    @SerializedName("USDCHF")
    USDCHF("USD/CHF", 5),

    @SerializedName("USDCAD")
    USDCAD("USD/CAD", 6),

    @SerializedName("AUDUSD")
    AUDUSD("AUD/USD", 7),

    @SerializedName("EURJPY")
    EURJPY("EUR/JPY", 8),

    @SerializedName("EURCHF")
    EURCHF("EUR/CHF", 9);

    private String slashName;
    private int order;

    public String getSlashName() {
        return slashName;
    }

    public int getOrder() {
        return order;
    }

    ToolType(String slashName, int order) {
        this.slashName = slashName;
        this.order = order;
    }

}
