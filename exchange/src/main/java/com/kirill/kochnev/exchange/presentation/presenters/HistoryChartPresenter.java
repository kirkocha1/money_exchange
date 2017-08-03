package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.HistoryInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IHistoryChartView;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 03.08.17.
 */

@InjectViewState
public class HistoryChartPresenter extends BasePresenter<IHistoryChartView> {

    private static final String TAG = "HistoryChartPresenter";
    private HistoryInteractor interactor;
    private TickTimer timer;
    private ToolType type;


    public HistoryChartPresenter(HistoryInteractor interactor, TickTimer timer, ToolType type) {
        this.interactor = interactor;
        this.timer = timer;
        this.type = type;
        getHistoryPoints();
    }

    private void getHistoryPoints() {
//        addToCompositeDisposable(interactor.getHistoryPoints(type)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(resultSet -> getViewState().feedChartWithPoints(resultSet),
//                        e -> Log.e(TAG, e.getMessage())));
        getTickStream();
    }

    private void getTickStream() {
        addToCompositeDisposable(interactor.getLivePoints(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultSet -> getViewState().mergeLivePoints(resultSet),
                        e -> Log.e(TAG, e.getMessage())));
    }


}
