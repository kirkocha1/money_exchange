package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.MessageType;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IRetry;
import com.kirill.kochnev.exchange.presentation.interfaces.ITickListView;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

/**
 * Presenter for {@link com.kirill.kochnev.exchange.presentation.views.TickListFragment}
 */
@InjectViewState
public class TickListPresenter extends BasePresenter<ITickListView> implements IRetry {
    private static final String TAG = "TickListPresenter";

    private TickInteractor interactor;
    private TickTimer timer;
    private boolean wasError = false;


    public TickListPresenter(TickInteractor interactor, TickTimer tickTimer) {
        this.interactor = interactor;
        this.timer = tickTimer;
        addToCompositeDisposable(interactor.getCachedTicks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(list -> subscribeOnStream())
                .subscribe(list -> {
                    getViewState().recreateList(list);
                }, e -> {
                    Log.e("ACTIVITY", "ERROR MESSAGE: " + e.getMessage());
                }));

    }

    @Override
    public void attachView(ITickListView view) {
        super.attachView(view);
        if (wasError) {
            retry();
            wasError = false;
        }
    }

    public void retry() {
        addToCompositeDisposable(interactor.restartStream().subscribe(this::subscribeOnStream, e -> {
            Log.e(TAG, "restart process failed");
        }));
    }

    private void subscribeOnStream() {
        clearDisposable();
        addToCompositeDisposable(interactor.getLiveTicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model.getMessageType().equals(MessageType.SUBSCRIPTION)) {
                        getViewState().recreateList(model.getTicks());
                    } else if ((model.getTicks() != null && model.getTicks().size() != 0)) {
                        timer.triger(model.getTicks(), list -> getViewState().invalidateList(list));
                    }
                }, e -> {
                    wasError = true;
                    Log.e("ACTIVITY", "ERROR MESSAGE: " + e.getMessage());
                    getViewState().showMessage("ERROR MESSAGE");
                }));
    }

    public void onLongClick(ToolType toolType) {
        interactor.changeNotification(toolType, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> Log.e(TAG, e.getMessage()));
    }

    @Override
    public void onDestroy() {
        interactor.disconnect().subscribe(() -> {
        }, e -> Log.e(TAG, e.getMessage()));
        super.onDestroy();
    }
}
