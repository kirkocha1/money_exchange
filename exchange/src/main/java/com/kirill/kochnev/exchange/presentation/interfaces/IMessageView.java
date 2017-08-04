package com.kirill.kochnev.exchange.presentation.interfaces;

/**
 * Created by kirill on 01.08.17.
 */

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Interface for showing message
 */
public interface IMessageView {

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
