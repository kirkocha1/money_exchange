package com.kirill.kochnev.exchange.domain.models;

import com.kirill.kochnev.exchange.data.enums.ToolType;

import java.math.BigDecimal;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TickUI {

    private ToolType type;

    private BigDecimal bid;

    private BigDecimal ask;

    private float spread;

    public ToolType getType() {
        return type;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public String getBidAndAskFormated() {
        return bid.toEngineeringString() + "/" + ask.toEngineeringString();
    }
}
