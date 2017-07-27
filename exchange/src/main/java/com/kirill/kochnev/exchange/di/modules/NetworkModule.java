package com.kirill.kochnev.exchange.di.modules;

import com.kirill.kochnev.exchange.data.mapper.TickMapper;
import com.kirill.kochnev.exchange.data.network.parser.PacketManager;
import com.kirill.kochnev.websocket.RxSocketWrapper;
import com.kirill.kochnev.websocket.Socket;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public RxSocketWrapper provideRxWrapper(Socket socket) {
        return new RxSocketWrapper(socket);
    }

    @Provides
    @Singleton
    public Socket provideSocket(@Named("serverUrl") String url) {
        return new Socket(url);
    }

    @Provides
    @Singleton
    public TickMapper provideTickMapper() {
        return new TickMapper();
    }


    @Provides
    @Singleton
    public PacketManager provideManager() {
        return new PacketManager();
    }

    @Provides
    @Named("serverUrl")
    String getServerUrl() {
        return "wss://quotes.exness.com:18400/";
    }
}
