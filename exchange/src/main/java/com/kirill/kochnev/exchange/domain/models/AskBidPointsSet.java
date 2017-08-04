package com.kirill.kochnev.exchange.domain.models;

import com.jjoe64.graphview.series.DataPoint;

import java.util.List;

/**
 * Created by kirill on 03.08.17.
 */

public class AskBidPointsSet {

    private List<Point> askPoints;

    private List<Point> bidPoints;

    private DataPoint[] asks;

    private DataPoint[] bids;

    public AskBidPointsSet(List<Point> askPoints, List<Point> bidPoints) {
        this.askPoints = askPoints;
        this.bidPoints = bidPoints;
        asks = mapToMass(askPoints);
        bids = mapToMass(bidPoints);
    }

    public DataPoint[] getAsks() {
        return asks;
    }

    public DataPoint[] getBids() {
        return bids;
    }

    private DataPoint[] mapToMass(List<Point> points) {
        DataPoint[] entries = new DataPoint[points.size()];
        int i = 0;
        for (Point point : points) {
            entries[i] = new DataPoint(point.getDate(), point.getVal().doubleValue());
            i++;
        }
        return entries;
    }

}
