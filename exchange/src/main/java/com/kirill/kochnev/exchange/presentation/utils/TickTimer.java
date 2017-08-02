package com.kirill.kochnev.exchange.presentation.utils;

import android.os.Handler;

import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kirill on 01.08.17.
 */

public class TickTimer {

    private HashMap<ToolType, TickUI> toolTickUi;
    private Handler handler;
    private TrigerUIListener callback;
    private int seconds;

    public TickTimer(Handler handler, int seconds) {
        this.handler = handler;
        this.seconds = seconds;
    }

    public void triger(List<TickUI> tickUIs, TrigerUIListener timerCallback) {
        putFreshTicks(tickUIs);
        if (callback == null) {
            this.callback = timerCallback;
            handler.postDelayed(() -> {
                if (this.callback != null && toolTickUi.size() != 0) {
                    timerCallback.triger(new ArrayList<>(toolTickUi.values()));
                }
                this.callback = null;
                toolTickUi.clear();
            }, seconds * 1000);
        }
    }

    public interface TrigerUIListener {
        void triger(List<TickUI> tickUIs);
    }

    private void putFreshTicks(List<TickUI> tickUIs) {
        if (toolTickUi == null) {
            toolTickUi = new HashMap<>();
        }
        for (TickUI tick : tickUIs) {
            toolTickUi.put(tick.getType(), tick);
        }
    }


}
