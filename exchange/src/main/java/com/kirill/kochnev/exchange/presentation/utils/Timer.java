package com.kirill.kochnev.exchange.presentation.utils;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class Timer {

    private static final int MILLISECONDS_COEFF = 1000;

    private long startTime;
    private int seconds;
    private boolean isLaunched = false;

    public Timer(int seconds) {
        this.seconds = seconds;
        startTimer();
    }

    public boolean isLeft() {
        return (System.currentTimeMillis() - startTime) / MILLISECONDS_COEFF >= seconds;
    }

    public void startTimer() {
        isLaunched = true;
        startTime = System.currentTimeMillis();
    }

    public boolean isLaunched() {
        return isLaunched;
    }
}
