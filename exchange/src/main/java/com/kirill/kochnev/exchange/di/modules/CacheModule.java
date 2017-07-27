package com.kirill.kochnev.exchange.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kirill.kochnev.exchange.data.db.AppDb;
import com.kirill.kochnev.exchange.data.db.TicksDataSource;
import com.kirill.kochnev.exchange.data.db.dao.TickDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Module
public class CacheModule {

    private Context context;

    public CacheModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public AppDb provideDb(Context context) {
        return Room.databaseBuilder(context, AppDb.class, "exchange.db").build();
    }

    @Singleton
    @Provides
    public TickDao provideTickDao(AppDb db) {
        return db.getTickDao();
    }

    @Singleton
    @Provides
    public TicksDataSource provideTicksDataSource(TickDao dao) {
        return new TicksDataSource(dao);
    }

}
