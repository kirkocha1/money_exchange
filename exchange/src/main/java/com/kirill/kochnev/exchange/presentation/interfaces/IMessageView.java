package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.MvpView;

/**
 * Created by kirill on 01.08.17.
 */

public interface IMessageView extends MvpView {
    void showMessage(String message);
}
