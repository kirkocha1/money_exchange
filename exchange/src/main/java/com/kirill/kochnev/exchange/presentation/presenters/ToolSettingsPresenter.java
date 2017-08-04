package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IToolSettingsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

/**
 * Presenter for {@link com.kirill.kochnev.exchange.presentation.views.ToolSettingsFragment}
 */
@InjectViewState
public class ToolSettingsPresenter extends BasePresenter<IToolSettingsView> {

    private static final String TAG = "ToolSettingsPresenter";
    private TickInteractor interactor;

    public ToolSettingsPresenter(TickInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void attachView(IToolSettingsView view) {
        super.attachView(view);
        clearDisposable();
        addToCompositeDisposable(interactor.getSubscriptionToolTypeList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().invalidateToolTypeList(list), e -> {
                    Log.e(TAG, "was't restored");
                }));
    }

    public void changeToolTypeModification(boolean isChecked, ToolType type) {
        addToCompositeDisposable(interactor.changeNotification(type, isChecked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Log.d(TAG, "Change tool type subscription: " + isChecked + " tool type: " + type);
                }, e -> {
                    Log.e(TAG, e.getMessage());
                    getViewState().dropToolType(type);
                    getViewState().showMessage(e.getMessage());
                }));
    }
}
