package com.kirill.kochnev.exchange.data.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class SubscribtionResponse implements IBaseModel {

    @SerializedName("subscribed_count")
    public int count;

    @SerializedName("subscribed_list")
    public SubList subList;

    @Override
    public boolean isEmptyList() {
        return subList.isEmptyList();
    }

    @Override
    public List<Tick> getList() {
        return subList.ticks;
    }
}
