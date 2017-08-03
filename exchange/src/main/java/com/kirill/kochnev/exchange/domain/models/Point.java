package com.kirill.kochnev.exchange.domain.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kirill on 03.08.17.
 */

public class Point {

    private BigDecimal val;

    private Date date;

    public Point(BigDecimal val, Date date) {
        this.val = val;
        this.date = date;
    }

    public BigDecimal getVal() {
        return val;
    }

    public Date getDate() {
        return date;
    }
}
