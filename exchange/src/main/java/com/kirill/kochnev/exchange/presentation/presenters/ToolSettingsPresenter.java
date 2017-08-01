package com.kirill.kochnev.exchange.presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IToolSettingsView;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

@InjectViewState
public class ToolSettingsPresenter extends BasePresenter<IToolSettingsView> {

    private static final String TAG = "ToolSettingsPresenter";
    private TickInteractor interactor;

    public ToolSettingsPresenter(TickInteractor interactor) {
        this.interactor = interactor;
        addToCompositeDisposable(interactor.getSubscriptionToolTypeList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().invalidateToolTypeList(list), e -> {
                    Log.e(TAG, "was't restored");
                }));
    }

    public void changeToolTypeModification(boolean isChecked, ToolType type) {
        addToCompositeDisposable(interactor.changeNotification(type, isChecked).subscribe(() -> {
            Log.d(TAG, "Change tool type subscription: " + isChecked + " tool type: " + type);
        }, e -> {
            Log.e(TAG, e.getMessage());
            getViewState().droptToolType(type);
            getViewState().showMessage(e.getMessage());
        }));
    }
}
