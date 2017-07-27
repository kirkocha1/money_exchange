package com.kirill.kochnev.exchange.data.db.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */
@Entity(tableName = "ticks")
public class TickDb {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "creation_date")
    private long date;

    @ColumnInfo(name = "tool_type")
    private String toolType;

    @ColumnInfo(name = "bid")
    private double bid;

    @ColumnInfo(name = "ask")
    private double ask;

    @ColumnInfo(name = "spread")
    private float spread;


    public TickDb() {
        this.date = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }
}
