package com.kirill.kochnev.exchange.presentation.views.charts;

import android.graphics.Color;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;

/**
 * Created by kirill on 03.08.17.
 */


/**
 * class that encapsulate graph logic
 */
public class GraphRenderer {

    private LineGraphSeries<DataPoint> asks;
    private LineGraphSeries<DataPoint> bids;
    private boolean isFirstTime = true;

    /**
     * Method makes initial initialization
     *
     * @param graph special view which contains graph
     * @param title label
     */
    public void adjustGraph(GraphView graph, String title) {
        graph.setTitle(title);
        graph.setTitleTextSize(30f);
        graph.setTitleColor(R.color.colorPrimary);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getViewport().setScalableY(false);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setHumanRounding(true);

    }

    /**
     * merge nw point to graph
     *
     * @param graph special view which contains graph
     * @param set   new points
     */
    public void mergeNewData(GraphView graph, AskBidPointsSet set) {
        if (isFirstTime) {
            asks = new LineGraphSeries<>(set.getAsks());
            graph.getViewport().setXAxisBoundsManual(true);
            double lapse = (set.getAsks()[set.getAsks().length - 1].getX() - set.getAsks()[0].getX()) / 2;
            graph.getViewport().setMinX(set.getAsks()[0].getX() + lapse);
            graph.getViewport().setMaxX(set.getAsks()[set.getAsks().length - 1].getX());
            initSeries(asks, "ASK", Color.RED);
            bids = new LineGraphSeries<>(set.getBids());
            initSeries(asks, "BID", Color.GREEN);
            isFirstTime = false;
            graph.addSeries(asks);
            graph.addSeries(bids);
        } else {
            for (DataPoint entry : set.getAsks()) {
                asks.appendData(entry, false, 400);
            }
            for (DataPoint entry : set.getBids()) {
                bids.appendData(entry, false, 400);
            }
            graph.getViewport().setMaxX(set.getBids()[set.getAsks().length - 1].getX());
        }
        graph.getViewport().scrollToEnd();
    }

    private void initSeries(LineGraphSeries series, String title, int color) {
        series.setTitle(title);
        series.setColor(color);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        series.setThickness(8);
    }
}
