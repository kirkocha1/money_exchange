package com.kirill.kochnev.exchange.repositories;

import android.util.Log;

import com.kirill.kochnev.exchange.data.db.TicksDataSource;
import com.kirill.kochnev.exchange.data.enums.MessageType;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.data.mapper.TickMapper;
import com.kirill.kochnev.exchange.data.network.models.CommonModel;
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

    private static final String TAG = "TickRepository";
    private RxSocketWrapper socket;
    private TicksDataSource ticksCache;
    private PacketManager manager;
    private TickMapper mapper;
    private PrefManager preferenceManager;

    public TickRepository(RxSocketWrapper socket, TicksDataSource ticksCache, PacketManager manager, TickMapper mapper, PrefManager preferenceManager) {
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

    public Completable addNewTool(ToolType type) {
        return Completable.fromAction(() -> {
            preferenceManager.putBoolean(type.toString(), true);
            socket.sendMessage("SUBSCRIBE: " + type.toString());
        });
    }

    public Completable deleteNewTool(ToolType type) {
        return Completable.fromAction(() -> {
            preferenceManager.putBoolean(type.toString(), false);
            socket.sendMessage("UNSUBSCRIBE: " + type.toString());
        });
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

    private void restoreToolsSubscribtion() throws Exception {
        String message = "SUBSCRIBE: " + buildToolChain();
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
