package com.kirill.kochnev.exchange.domain.interactors;

import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.data.mapper.TickDbMapper;
import com.kirill.kochnev.exchange.domain.mappers.PointMapper;
import com.kirill.kochnev.exchange.domain.models.AskBidPointsSet;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by kirill on 03.08.17.
 */

public class HistoryInteractor {

    private TickRepository repository;
    private PointMapper mapper;

    public HistoryInteractor(TickRepository repository, PointMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Single<AskBidPointsSet> getHistoryPoints(ToolType type) {
        return repository.getCachedTicksByToolType(type).map(mapper::mapToSet);
    }

    public Observable<AskBidPointsSet> getLivePoints(ToolType type) {
        return repository.getFilteredTicks(type).map(mapper::mapToSet);
    }
}
