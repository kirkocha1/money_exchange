package com.kirill.kochnev.exchange.di.modules;

import com.kirill.kochnev.exchange.data.db.TicksDataSource;
import com.kirill.kochnev.exchange.data.mapper.TickDbMapper;
import com.kirill.kochnev.exchange.data.network.parser.PacketManager;
import com.kirill.kochnev.exchange.presentation.utils.PrefManager;
import com.kirill.kochnev.exchange.repositories.TickRepository;
import com.kirill.kochnev.websocket.RxSocketWrapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public TickRepository provideTickRepository(TicksDataSource cacheSource, RxSocketWrapper socketWrapper, TickDbMapper mapper, PacketManager manager, PrefManager preferenceManager) {
        return new TickRepository(socketWrapper, cacheSource, manager, mapper, preferenceManager);
    }
}
