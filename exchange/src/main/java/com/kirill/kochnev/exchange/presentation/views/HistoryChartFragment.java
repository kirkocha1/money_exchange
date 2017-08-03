package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.HistoryInteractor;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.presentation.interfaces.IHistoryChartView;
import com.kirill.kochnev.exchange.presentation.presenters.HistoryChartPresenter;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;
import com.kirill.kochnev.exchange.presentation.views.charts.ChartRenderer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 02.08.17.
 */

public class HistoryChartFragment extends MvpAppCompatFragment implements IHistoryChartView {

    public static final String TOOL_TYPE = "tool_type";

    public static final HistoryChartFragment createFragment(ToolType type) {
        HistoryChartFragment fragment = new HistoryChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOOL_TYPE, type.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    private ChartRenderer renderer = new ChartRenderer();

    @BindView(R.id.main_chart)
    LineChart lineChart;

    @Inject
    HistoryInteractor interactor;

    @Inject
    TickTimer tickTimer;

    @InjectPresenter
    HistoryChartPresenter presenter;

    @ProvidePresenter
    HistoryChartPresenter providePresenter() {
        return new HistoryChartPresenter(interactor, tickTimer, ToolType.valueOf(getArguments().getString(TOOL_TYPE)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExchangeApplication.getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void showMessage(String message) {

    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        renderer.adjustChart(lineChart);
        lineChart.setData(new LineData());
    }

    @Override
    public void feedChartWithPoints(AskBidPointsSet set) {
        renderer.adjustChart(lineChart);
        LineDataSet askSet = new LineDataSet(set.getAskEntries(), "ASK");
        askSet.setDrawValues(false);
        LineDataSet bidSet = new LineDataSet(set.getBidEntries(), "BID");
        bidSet.setColor(R.color.red);
        bidSet.setDrawValues(false);
        LineData resultData = new LineData();
        resultData.addDataSet(askSet);
        resultData.addDataSet(bidSet);
        lineChart.setData(new LineData(askSet));
        lineChart.invalidate();
    }

    @Override
    public void mergeLivePoints(AskBidPointsSet set) {
        if (set.getAskPoints().size() != 0) {
            LineData data = lineChart.getData();
            if (data.getDataSets().size() == 0) {
                LineDataSet askSet = new LineDataSet(set.getAskEntries(), "ASK");
                askSet.setDrawValues(false);
                LineDataSet bidSet = new LineDataSet(set.getBidEntries(), "BID");
                bidSet.setColor(R.color.red);
                bidSet.setDrawValues(false);
                data.addDataSet(askSet);
                data.addDataSet(bidSet);
            } else {
                renderer.mergeNewData(lineChart, data, set);
            }
        }

    }
}
