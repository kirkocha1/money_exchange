package com.kirill.kochnev.exchange.domain.mappers;

import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.data.network.models.Tick;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

/**
 * for mapping {@link TickDb} and {@link Tick}
 */
public class TickUiMapper {

    public TickUI map(TickDb tickDb) {
        TickUI tickUI = new TickUI();
        tickUI.setType(ToolType.valueOf(tickDb.getToolType()));
        tickUI.setBid(new BigDecimal(tickDb.getBid()).setScale(5, BigDecimal.ROUND_DOWN));
        tickUI.setAsk(new BigDecimal(tickDb.getBid()).setScale(5, BigDecimal.ROUND_DOWN));
        tickUI.setSpread(tickDb.getSpread());
        return tickUI;
    }

    public List<TickUI> mapToUiList(List<TickDb> tickDbs) {
        List<TickUI> ticks = new ArrayList<>();
        for (TickDb tickDb : tickDbs) {
            ticks.add(map(tickDb));
        }
        return ticks;
    }

    public TickUI map(Tick tick) {
        TickUI tickUI = new TickUI();
        tickUI.setType(tick.toolType);
        tickUI.setBid((tick.bid.setScale(5, BigDecimal.ROUND_DOWN)));
        tickUI.setAsk((tick.ask).setScale(5, BigDecimal.ROUND_DOWN));
        tickUI.setSpread(tick.spread);
        return tickUI;
    }

    public List<TickUI> mapList(List<Tick> ticks) {
        List<TickUI> tickUIs = new ArrayList<>();
        for (Tick tick : ticks) {
            tickUIs.add(map(tick));
        }
        return tickUIs;
    }
}
