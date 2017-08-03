package com.kirill.kochnev.exchange.di.components;

import com.kirill.kochnev.exchange.di.modules.CacheModule;
import com.kirill.kochnev.exchange.di.modules.InteractorModule;
import com.kirill.kochnev.exchange.di.modules.NetworkModule;
import com.kirill.kochnev.exchange.di.modules.RepositoryModule;
import com.kirill.kochnev.exchange.di.modules.UtilsModule;
import com.kirill.kochnev.exchange.presentation.views.HistoryChartFragment;
import com.kirill.kochnev.exchange.presentation.views.MainActivity;
import com.kirill.kochnev.exchange.presentation.views.TickListFragment;
import com.kirill.kochnev.exchange.presentation.views.ToolSettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Singleton
@Component(modules = {CacheModule.class, NetworkModule.class, RepositoryModule.class, InteractorModule.class, UtilsModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(TickListFragment fragment);

    void inject(ToolSettingsFragment fragment);

    void inject(HistoryChartFragment fragment);
}
