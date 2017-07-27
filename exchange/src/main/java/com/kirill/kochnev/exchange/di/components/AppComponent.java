package com.kirill.kochnev.exchange.di.components;

import com.kirill.kochnev.exchange.di.modules.CacheModule;
import com.kirill.kochnev.exchange.di.modules.NetworkModule;
import com.kirill.kochnev.exchange.di.modules.RepositoryModule;
import com.kirill.kochnev.exchange.presentation.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Singleton
@Component(modules = {CacheModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
