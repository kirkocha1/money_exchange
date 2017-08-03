package com.kirill.kochnev.exchange.di.modules;

import com.kirill.kochnev.exchange.domain.interactors.HistoryInteractor;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.domain.mappers.PointMapper;
import com.kirill.kochnev.exchange.domain.mappers.TickUiMapper;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

@Module
public class InteractorModule {

    @Provides
    public TickUiMapper provideTickUIMapper() {
        return new TickUiMapper();
    }

    @Provides
    public PointMapper providePointMapper() {
        return new PointMapper();
    }


    @Singleton
    @Provides
    public TickInteractor provideTickInteractor(TickRepository repository, TickUiMapper mapper) {
        return new TickInteractor(repository, mapper);
    }

    @Singleton
    @Provides
    public HistoryInteractor provideHistoryInteractor(TickRepository repository, PointMapper mapper) {
        return new HistoryInteractor(repository, mapper);
    }
}
