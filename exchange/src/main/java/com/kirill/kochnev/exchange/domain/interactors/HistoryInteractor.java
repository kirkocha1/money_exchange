package com.kirill.kochnev.exchange.domain.interactors;

import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.mappers.PointMapper;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.presentation.interfaces.IRestart;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by kirill on 03.08.17.
 */

/**
 * instance of this class is used for Chart drawing business logic
 */
public class HistoryInteractor implements IRestart {

    private TickRepository repository;
    private PointMapper mapper;

    public HistoryInteractor(TickRepository repository, PointMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    /**
     * Method which request points for chart
     *
     * @param type of pair
     * @return {@link Single with points for graph}
     */
    public Single<AskBidPointsSet> getHistoryPoints(ToolType type) {
        return repository.getCachedTicksByToolType(type).map(mapper::mapToSet);
    }

    /**
     * request live stream of points
     *
     * @param type of pair
     * @return {@link Observable} which emits real time points for chart
     */
    public Observable<AskBidPointsSet> getLivePoints(ToolType type) {
        return repository.getFilteredTicks(type).map(mapper::mapToSet);
    }

    @Override
    public Completable restartStream() {
        return repository.restartStream();
    }
}
