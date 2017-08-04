package com.kirill.kochnev.exchange.presentation.interfaces;

import io.reactivex.Completable;

/**
 * Created by kirill on 04.08.17.
 */


/**
 * interface for restartin data stream used for delegation purposes
 */
public interface IRestart {
    Completable restartStream();
}
