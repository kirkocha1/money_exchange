package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.List;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */


public interface ITickListView extends IMessageView {

    @StateStrategyType(SingleStateStrategy.class)
    void recreateList(List<TickUI> list);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void invalidateList(List<TickUI> list);

}
