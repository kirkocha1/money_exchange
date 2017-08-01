package com.kirill.kochnev.exchange.presentation.utils;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class Timer {

    private static final int MILLISECONDS_COEFF = 1000;

    private long startTime;
    private int seconds;
    private boolean isFinished = true;

    public Timer(int seconds) {
        this.seconds = seconds;
    }

    public boolean isLeft() {
        isFinished = (System.currentTimeMillis() - startTime) / MILLISECONDS_COEFF >= seconds;
        return isFinished;
    }

    public void startTimer() {
        isFinished = false;
        startTime = System.currentTimeMillis();
    }

    public boolean isFinished() {
        return isFinished;
    }
}
