package com.kirill.kochnev.exchange.domain.mappers;

import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.domain.models.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kirill on 03.08.17.
 */

public class PointMapper {

    private Point mapAskVal(TickDb tick) {
        return new Point(new BigDecimal(tick.getAsk()), new Date(tick.getDate()));
    }

    private Point mapBidVal(TickDb tick) {
        return new Point(new BigDecimal(tick.getBid()), new Date(tick.getDate()));
    }

    public AskBidPointsSet mapToSet(List<TickDb> ticks) {

        List<Point> askPoints = new ArrayList<>();
        List<Point> bidPoints = new ArrayList<>();
        for (TickDb tickDb : ticks) {
            askPoints.add(mapAskVal(tickDb));
            bidPoints.add(mapBidVal(tickDb));
        }
        return new AskBidPointsSet(askPoints, bidPoints);
    }
}
