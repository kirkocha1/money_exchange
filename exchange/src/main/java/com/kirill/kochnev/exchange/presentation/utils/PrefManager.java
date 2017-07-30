package com.kirill.kochnev.exchange.presentation.utils;

import android.content.SharedPreferences;

/**
 * Created by Kirill Kochnev on 29.07.17.
 */

public class PrefManager {

    private SharedPreferences preferences;

    public PrefManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
}
