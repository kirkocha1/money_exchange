package com.kirill.kochnev.exchange.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.kirill.kochnev.exchange.presentation.utils.PrefManager;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

@Module
public class UtilsModule {

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("EXCHANGE", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public PrefManager providePrefManager(SharedPreferences preferences) {
        return new PrefManager(preferences);
    }

    @Provides
    public TickTimer provideTimer() {
        return new TickTimer(new Handler(), 2);
    }

}
