package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.List;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */


public interface ITickListView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void recreateList(List<TickUI> list);

    void invalidateList(List<TickUI> list);
}
