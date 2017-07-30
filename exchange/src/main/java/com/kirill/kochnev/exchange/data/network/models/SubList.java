package com.kirill.kochnev.exchange.data.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class SubList extends CommonModel {

    @SerializedName("ticks")
    public List<Tick> ticks;

    @Override
    public boolean isEmptyList() {
        return ticks == null || ticks.size() == 0;
    }

    @Override
    public List<Tick> getList() {
        return ticks;
    }

}
