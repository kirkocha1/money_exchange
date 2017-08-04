package com.kirill.kochnev.exchange.presentation.views.charts;

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
            initSeries(asks, "ASK", R.color.red);
            bids = new LineGraphSeries<>(set.getBids());
            initSeries(asks, "BID", R.color.green);
            isFirstTime = false;
            graph.addSeries(asks);
            graph.addSeries(bids);
        } else {
            for (DataPoint entry : set.getAsks()) {
                asks.appendData(entry, false, 1500);
            }
            for (DataPoint entry : set.getBids()) {
                bids.appendData(entry, false, 1500);
            }
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
