package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.MessageType;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.domain.models.TickUI;
import com.kirill.kochnev.exchange.presentation.interfaces.ITickListView;
import com.kirill.kochnev.exchange.presentation.utils.Timer;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

@InjectViewState
public class TickListPresenter extends BasePresenter<ITickListView> {
    public static final String TAG = "TickListPresenter";
    private TickInteractor interactor;
    private Timer timer = new Timer(3);

    public TickListPresenter(TickInteractor interactor) {
        this.interactor = interactor;
        subscribeOnStream();
    }

    private void subscribeOnStream() {
        addToCompositeDisposable(interactor.getLiveTicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model.getMessageType().equals(MessageType.SUBSCRIPTION)) {
                        getViewState().recreateList(model.getTicks());
                    } else if ((model.getTicks() != null && model.getTicks().size() != 0)
                            && (!timer.isLaunched() || timer.isLeft())) {
                        timer.startTimer();
                        Log.e(TAG, model.getTicks().size() + "");
                        for (TickUI tickUI : model.getTicks()) {
                            Log.e(TAG, tickUI.getType().getSlashName());
                        }
                        getViewState().invalidateList(model.getTicks());
                    }
                }, e -> {
                    Log.e("ACTIVITY", "ERROR MESSAGE: " + e.getMessage());
                }));
    }
}
