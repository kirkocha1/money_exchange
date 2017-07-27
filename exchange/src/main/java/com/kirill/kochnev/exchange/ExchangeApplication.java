package com.kirill.kochnev.exchange;

import android.app.Application;

import com.kirill.kochnev.exchange.di.components.AppComponent;
import com.kirill.kochnev.exchange.di.components.DaggerAppComponent;
import com.kirill.kochnev.exchange.di.modules.CacheModule;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class ExchangeApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().cacheModule(new CacheModule(this)).build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
