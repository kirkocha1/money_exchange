package com.kirill.kochnev.exchange.presentation.views.charts;

import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.domain.models.Point;

/**
 * Created by kirill on 03.08.17.
 */

public class ChartRenderer {

    public LineChart adjustChart(LineChart chart) {
        chart.setScaleXEnabled(true);
        chart.setScaleYEnabled(false);
        chart.setHorizontalScrollBarEnabled(true);
        XAxis xAxis = chart.getXAxis();
        chart.setVisibleXRangeMaximum(50);
        YAxis leftAxis = chart.getAxis(YAxis.AxisDependency.LEFT);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);
        return chart;
    }

    public void mergeNewData(LineChart chart, LineData lineData, AskBidPointsSet set) {
        for (Point entry : set.getAskPoints()) {
            lineData.addEntry(new Entry(lineData.getDataSetByIndex(0).getEntryCount(), entry.getVal().floatValue()), 0);
            Log.e("MERGE", "ASK ADDED");
        }
        for (Point entry : set.getBidPoints()) {
            lineData.addEntry(new Entry(lineData.getDataSetByIndex(1).getEntryCount(), entry.getVal().floatValue()), 1);
            Log.e("MERGE", "BID ADDED");
        }
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.setVisibleXRangeMaximum(50);
        chart.moveViewToX(lineData.getEntryCount());
    }

}
