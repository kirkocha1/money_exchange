package com.kirill.kochnev.exchange.domain.models;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 03.08.17.
 */

public class AskBidPointsSet {

    private List<Point> askPoints;

    private List<Point> bidPoints;

    private List<Entry> askEntryPoints;

    private List<Entry> bidEntryPoints;

    public AskBidPointsSet(List<Point> askPoints, List<Point> bidPoints) {
        this.askPoints = askPoints;
        this.bidPoints = bidPoints;
        askEntryPoints = mapToEntries(askPoints);
        bidEntryPoints = mapToEntries(bidPoints);
    }

    public List<Point> getAskPoints() {
        return askPoints;
    }

    public List<Point> getBidPoints() {
        return bidPoints;
    }

    public List<Entry> getAskEntries() {
        return askEntryPoints;
    }

    public List<Entry> getBidEntries() {
        return bidEntryPoints;
    }

    private List<Entry> mapToEntries(List<Point> points) {
        List<Entry> entries = new ArrayList<>();
        for (Point point : points) {
            entries.add(new Entry(points.indexOf(point), point.getVal().floatValue()));
        }
        return entries;
    }

}
