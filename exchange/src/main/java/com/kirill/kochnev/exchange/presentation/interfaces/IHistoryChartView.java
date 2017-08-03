package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;

/**
 * Created by kirill on 03.08.17.
 */

public interface IHistoryChartView extends MvpView, IMessageView {

    void feedChartWithPoints(AskBidPointsSet set);

    void mergeLivePoints(AskBidPointsSet set);


}
