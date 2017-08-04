package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.HistoryInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IHistoryChartView;
import com.kirill.kochnev.exchange.presentation.interfaces.IRetry;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 03.08.17.
 */

/**
 * Presenter for {@link com.kirill.kochnev.exchange.presentation.views.HistoryChartFragment}
 */
@InjectViewState
public class HistoryChartPresenter extends BasePresenter<IHistoryChartView> implements IRetry {

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
        addToCompositeDisposable(interactor.getHistoryPoints(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(set -> getTickStream())
                .subscribe(resultSet -> getViewState().mergePoints(resultSet),
                        e -> Log.e(TAG, e.getMessage())));
    }

    private void getTickStream() {
        clearDisposable();
        addToCompositeDisposable(interactor.getLivePoints(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultSet -> getViewState().mergePoints(resultSet),
                        e -> {
                            Log.e(TAG, e.getMessage());
                            getViewState().showMessage(e.getMessage());
                        }));
    }

    public void retry() {
        addToCompositeDisposable(interactor.restartStream().subscribe(this::getTickStream, e -> {
            Log.e(TAG, "restart process failed");
        }));
    }

}
