package com.kirill.kochnev.exchange.data.network.models;

import com.kirill.kochnev.exchange.data.db.models.TickDb;

import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public interface IBaseModel {

    List<Tick> getList();

    boolean isEmptyList();
}
