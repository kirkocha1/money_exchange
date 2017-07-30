package com.kirill.kochnev.exchange.presentation.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void addToCompositeDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
