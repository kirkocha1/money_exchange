package com.kirill.kochnev.exchange.presentation.utils;

import android.support.v4.app.FragmentManager;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.presentation.views.TickListFragment;
import com.kirill.kochnev.exchange.presentation.views.ToolSettingsFragment;

/**
 * Created by kirill on 02.08.17.
 */

public class FragmentNavigator {

    public static final String TICKS_SCREEN = "TICKS_SCREEN";
    public static final String SETTINGS_SCREEN = "SETTINGS_SCEEN";
    public static final String TICK_HISITORY_SCREEN = "TICK_HISITORY_SCREEN";

    private FragmentManager manager;
    private ScreenState state;

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void navigateTo(String screenName, OnNavigateAction action) {
        switch (screenName) {
            case TICK_HISITORY_SCREEN:
                break;
            case TICKS_SCREEN:
                state = ScreenState.TICKS;
                moveToTicksScreen(action);
                break;
            case SETTINGS_SCREEN:
                state = ScreenState.SETTINGS;
                moveToSettings(action);
                break;

        }
    }

    public void navigateTo(String screenName) {
        navigateTo(screenName, null);
    }

    public void back(OnNavigateAction action) {
        if (action != null) {
            action.beforeNavigate();
        }
        if (manager != null) {
            manager.popBackStack();
        }
    }

    private void moveToTicksScreen(OnNavigateAction action) {
        if (manager != null) {
            if (action != null) {
                action.beforeNavigate();
            }
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                    .replace(R.id.container, new TickListFragment(), TickListFragment.TAG)
                    .commit();
        }
    }

    private void moveToSettings(OnNavigateAction action) {
        if (manager != null) {
            if (action != null) {
                action.beforeNavigate();
            }
            if (state.equals(ScreenState.SETTINGS)) {
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                        .addToBackStack(ToolSettingsFragment.TAG)
                        .hide(manager.findFragmentByTag(TickListFragment.TAG))
                        .add(R.id.container, new ToolSettingsFragment())
                        .commit();
            }
        }


    }

    public interface OnNavigateAction {
        void beforeNavigate();
    }

    enum ScreenState {
        TICKS,
        SETTINGS
    }
}
