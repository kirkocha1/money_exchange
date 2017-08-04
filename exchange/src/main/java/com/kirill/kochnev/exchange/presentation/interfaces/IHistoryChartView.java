package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;

/**
 * Created by kirill on 03.08.17.
 */

/**
 * Interface for {@link com.kirill.kochnev.exchange.presentation.views.HistoryChartFragment}
 */
public interface IHistoryChartView extends MvpView, IMessageView {

    /**
     * add new points
     *
     * @param set of point
     */
    void mergePoints(AskBidPointsSet set);
}
