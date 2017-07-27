package com.kirill.kochnev.exchange.repositories;

import com.kirill.kochnev.exchange.data.db.TicksDataSource;
import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.mapper.TickMapper;
import com.kirill.kochnev.exchange.data.network.models.IBaseModel;
import com.kirill.kochnev.exchange.data.network.parser.PacketManager;
import com.kirill.kochnev.websocket.RxSocketWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class TickRepository {

    private RxSocketWrapper socket;
    private TicksDataSource ticksCache;
    private PacketManager manager;
    private TickMapper mapper;

    public TickRepository(RxSocketWrapper socket, TicksDataSource ticksCache, PacketManager manager, TickMapper mapper) {
        this.socket = socket;
        this.ticksCache = ticksCache;
        this.manager = manager;
        this.mapper = mapper;
    }

    public Observable<List<TickDb>> getTicks() {
        return socket.getSocketObservable().map(rawResponse -> {
            List<TickDb> result = new ArrayList<>();
            IBaseModel response = manager.parse(rawResponse);
            if (!response.isEmptyList()) {
                result = mapper.mapList(response.getList());
            }
            return result;
        });
    }

    public Completable sendMessage(String message) {
        return socket.sendMessage(message);
    }
}
