package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jjoe64.graphview.GraphView;
import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.HistoryInteractor;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.presentation.interfaces.IHistoryChartView;
import com.kirill.kochnev.exchange.presentation.presenters.HistoryChartPresenter;
import com.kirill.kochnev.exchange.presentation.utils.ErrorHandler;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;
import com.kirill.kochnev.exchange.presentation.views.charts.GraphRenderer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 02.08.17.
 */

public class HistoryChartFragment extends BaseActionBarFragment implements IHistoryChartView {

    public static final String TOOL_TYPE = "tool_type";

    public static final HistoryChartFragment createFragment(ToolType type) {
        HistoryChartFragment fragment = new HistoryChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOOL_TYPE, type.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    private GraphRenderer renderer = new GraphRenderer();

    @BindView(R.id.main_graph)
    GraphView graph;

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
        ErrorHandler.showSnackBar(graph, message, presenter);
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        renderer.adjustGraph(graph, getArguments().getString(TOOL_TYPE));
    }
    
    @Override
    public void mergePoints(AskBidPointsSet set) {
        if (set.getAsks().length != 0 & set.getBids().length != 0) {
            renderer.mergeNewData(graph, set);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getArguments().getString(TOOL_TYPE));
        hideBackButton(false);
    }
}
