package com.kirill.kochnev.exchange.domain.interactors;

import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.mappers.TickUiMapper;
import com.kirill.kochnev.exchange.domain.models.CommonUiModel;
import com.kirill.kochnev.exchange.domain.models.TickUI;
import com.kirill.kochnev.exchange.presentation.interfaces.IRestart;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

/**
 * class for working with ticks. Represents business logic
 */
public class TickInteractor implements IRestart {
    public static final String TAG = "TickInteractor";
    private TickRepository repository;
    private TickUiMapper mapper;

    public TickInteractor(TickRepository repository, TickUiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    /**
     * Method for getting cached ticks
     * @return {@link Single}
     */
    public Single<List<TickUI>> getCachedTicks() {
        return repository.getCachedTicks().map(mapper::mapToUiList);
    }


    /**
     * Method for getting real time ticks
     * @return {@link Observable} which emits tick
     */
    public Observable<CommonUiModel> getLiveTicks() {
        return repository.getTicks().map(model -> {
            CommonUiModel res = new CommonUiModel(model.getMessageType());
            if (!model.isEmptyList()) {
                res.setTicks(mapper.mapList(model.getList()));
            }
            return res;
        });
    }

    @Override
    public Completable restartStream() {
        return repository.restartStream();
    }

    /**
     * subscription on particular {@link ToolType}
     * @param type pair
     * @param isChecked
     * @return
     */
    public Completable changeNotification(ToolType type, boolean isChecked) {
        return isChecked ? repository.addNewTool(type) : repository.deleteNewTool(type);
    }

    /**
     * Method get all client pairs
     * @return {@link Single}
     */
    public Single<List<ToolType>> getSubscriptionToolTypeList() {
        return repository.getToolTypeList();
    }

    public Completable disconnect() {
        return repository.disconnecteFromSocket();
    }


}
