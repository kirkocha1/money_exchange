package com.kirill.kochnev.exchange.data.mapper;

import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.network.models.Tick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class TickDbMapper {

    public TickDb map(Tick tick) {
        TickDb tickDb = new TickDb();
        tickDb.setAsk(tick.ask.toEngineeringString());
        tickDb.setBid(tick.bid.toEngineeringString());
        tickDb.setSpread(tick.spread);
        tickDb.setToolType(tick.toolType.toString());
        return tickDb;
    }

    public List<TickDb> mapList(List<Tick> ticks) {
        List<TickDb> result = new ArrayList<>();
        for (Tick tick : ticks) {
            result.add(map(tick));
        }
        return result;
    }
}

