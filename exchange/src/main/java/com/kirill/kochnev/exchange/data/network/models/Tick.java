package com.kirill.kochnev.exchange.data.network.models;

import com.google.gson.annotations.SerializedName;
import com.kirill.kochnev.exchange.data.enums.ToolType;

import java.math.BigDecimal;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class Tick {

    @SerializedName("s")
    public ToolType toolType;

    @SerializedName("b")
    public BigDecimal bid;

    @SerializedName("a")
    public BigDecimal ask;

    @SerializedName("spr")
    public float spread;
}
