package com.kirill.kochnev.exchange.di.modules;

import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.domain.mappers.TickUiMapper;
import com.kirill.kochnev.exchange.repositories.TickRepository;

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
    public TickInteractor provideTickInteractor(TickRepository repository, TickUiMapper mapper) {
        return new TickInteractor(repository, mapper);
    }
}
