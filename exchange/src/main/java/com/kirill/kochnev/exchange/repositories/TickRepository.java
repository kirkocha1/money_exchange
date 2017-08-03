package com.kirill.kochnev.exchange.repositories;

import android.util.Log;

import com.kirill.kochnev.exchange.data.db.TicksDataSource;
import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.enums.MessageType;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.data.mapper.TickDbMapper;
import com.kirill.kochnev.exchange.data.network.models.CommonModel;
import com.kirill.kochnev.exchange.data.network.models.Tick;
import com.kirill.kochnev.exchange.data.network.parser.PacketManager;
import com.kirill.kochnev.exchange.presentation.utils.PrefManager;
import com.kirill.kochnev.websocket.RxSocketWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class TickRepository {
    private static final String SUBSCRIBE_COMMAND = "SUBSCRIBE: ";
    private static final String UNSUBSCRIBE_COMMAND = "UNSUBSCRIBE: ";
    private static final String TAG = "TickRepository";
    private RxSocketWrapper socket;
    private TicksDataSource ticksCache;
    private PacketManager manager;
    private TickDbMapper mapper;
    private PrefManager preferenceManager;

    public TickRepository(RxSocketWrapper socket, TicksDataSource ticksCache, PacketManager manager, TickDbMapper mapper, PrefManager preferenceManager) {
        this.socket = socket;
        this.ticksCache = ticksCache;
        this.manager = manager;
        this.mapper = mapper;
        this.preferenceManager = preferenceManager;
    }

    public Observable<CommonModel> getTicks() {
        return socket.getSocketObservable().map(rawResponse -> {
            CommonModel result = manager.parse(rawResponse);
            Log.e(TAG, rawResponse);
            if (result.getMessageType().equals(MessageType.CONNECTED)) {
                Log.e(TAG, "start restoring");
                restoreToolsSubscribtion();
            } else {
                if (!result.isEmptyList()) {
                    ticksCache.putTicks(mapper.mapList(result.getList()));
                }
            }
            return result;
        });
    }

    public Observable<List<TickDb>> getFilteredTicks(ToolType type) {
        return getTicks().map(commonModel -> {
            List<TickDb> result = new ArrayList<>();
            List<Tick> ticks = commonModel.getList();
            for (Tick tick : ticks) {
                if (tick.toolType.equals(type)) {
                    result.add(mapper.map(tick));
                }
            }
            return result;
        });
//                .flatMapIterable(ticks -> ticks)
//                .filter(tick -> tick.toolType.equals(type))
//                .toList()
//                .toObservable()
//                .map(mapper::mapList);
    }

    public Single<List<TickDb>> getCachedTicksByToolType(ToolType type) {
        return Single.fromCallable(() -> ticksCache.getTicksByToolType(type));
    }

    public Single<List<TickDb>> getCachedTicks() {
        return getToolTypeList().flatMap(list -> Single.fromCallable(() -> ticksCache.getSubscribedTicks(list)));
    }

    public Completable addNewTool(ToolType type) {
        return socket.sendMessageAsComplitable(SUBSCRIBE_COMMAND + type.toString())
                .andThen(Completable.fromAction(() -> preferenceManager.putBoolean(type.toString(), true)));
    }

    public Completable deleteNewTool(ToolType type) {
        return socket.sendMessageAsComplitable(UNSUBSCRIBE_COMMAND + type.toString())
                .andThen(Completable.fromAction(() -> preferenceManager.putBoolean(type.toString(), false)));
    }

    public Single<List<ToolType>> getToolTypeList() {
        return Single.fromCallable(() -> {
            List<ToolType> result = new ArrayList<>();
            for (ToolType toolType : ToolType.values()) {
                if (preferenceManager.getBoolean(toolType.toString())) {
                    result.add(toolType);
                }
            }
            return result;
        });
    }

    public Completable restartStream() {
        return socket.restart();
    }

    private void restoreToolsSubscribtion() throws Exception {
        String message = SUBSCRIBE_COMMAND + buildToolChain();
        socket.sendMessage(message);
    }


    private String buildToolChain() {
        StringBuilder toolChain = new StringBuilder();
        for (ToolType toolType : ToolType.values()) {
            if (preferenceManager.getBoolean(toolType.toString())) {
                toolChain.append(toolType.toString());
                toolChain.append(",");
            }
        }
        if (toolChain.length() != 0) {
            toolChain.deleteCharAt(toolChain.length() - 1);
        }
        Log.d(TAG, toolChain.toString());
        return toolChain.toString();
    }


}
