package com.kirill.kochnev.exchange.domain.interactors;

import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.mappers.TickUiMapper;
import com.kirill.kochnev.exchange.domain.models.CommonUiModel;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TickInteractor {
    public static final String TAG = "TickInteractor";
    private TickRepository repository;
    private TickUiMapper mapper;

    public TickInteractor(TickRepository repository, TickUiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Observable<CommonUiModel> getLiveTicks() {
        return repository.getTicks().map(model -> {
            CommonUiModel res = new CommonUiModel(model.getMessageType());
            if (!model.isEmptyList()) {
                res.setTicks(mapper.mapList(model.getList()));
            }
            return res;
        });
    }

    public Completable changeNotification(ToolType type, boolean isChecked) {
        return isChecked ? repository.addNewTool(type) : repository.deleteNewTool(type);
    }

    public Single<List<ToolType>> getSubscriptionToolTypeList() {
        return repository.getToolTypeList();
    }

}
