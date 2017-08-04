package com.kirill.kochnev.exchange.presentation.utils;

import android.support.v4.app.FragmentManager;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.presentation.views.HistoryChartFragment;
import com.kirill.kochnev.exchange.presentation.views.TickListFragment;
import com.kirill.kochnev.exchange.presentation.views.ToolSettingsFragment;

/**
 * Created by kirill on 02.08.17.
 */

/**
 * <p>
 * This class represents a simple fragment navigation in application
 * <p>
 * instance of this class should be set in Activity.onResume method
 *
 */
public class FragmentNavigator {

    public static final String TICKS_SCREEN = "TICKS_SCREEN";
    public static final String SETTINGS_SCREEN = "SETTINGS_SCEEN";
    public static final String TICK_HISITORY_SCREEN = "TICK_HISITORY_SCREEN";

    private FragmentManager manager;
    private ScreenState state;

    /**
     * interface for callback which should be applied before navigation
     */
    public interface OnNavigateAction {
        void beforeNavigate();
    }

    /**
     * Method is used for setting FragmentManger
     * @param manager
     */
    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void clear() {
        manager = null;
    }

    /**
     * Main method of FragmentNavigator class it makes navigation from one fragment to another
     * @param screenName
     * @param data
     * @param action
     */
    public void navigateTo(String screenName, Object data, OnNavigateAction action) {
        switch (screenName) {
            case TICK_HISITORY_SCREEN:
                moveToHistoryScreen(data, action);
                state = ScreenState.HISTORY;
                break;
            case TICKS_SCREEN:
                moveToTicksScreen(action);
                state = ScreenState.TICKS;
                break;
            case SETTINGS_SCREEN:
                moveToSettings(action);
                state = ScreenState.SETTINGS;
                break;
        }
    }

    public void navigateTo(String screenName) {
        navigateTo(screenName, null, null);
    }

    /**
     * when need to go to previous fragment
     * @param action
     */
    public void back(OnNavigateAction action) {
        if (action != null) {
            action.beforeNavigate();
        }
        if (manager != null) {
            manager.popBackStack();
        }
    }

    private void moveToHistoryScreen(Object data, OnNavigateAction action) {
        if (manager != null && data instanceof ToolType) {
            if (action != null) {
                action.beforeNavigate();
            }
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                    .addToBackStack(ScreenState.HISTORY.toString())
                    .replace(R.id.container, HistoryChartFragment.createFragment((ToolType) data))
                    .commit();
        }
    }

    private void moveToTicksScreen(OnNavigateAction action) {
        if (manager != null) {
            if (action != null) {
                action.beforeNavigate();
            }
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                    .replace(R.id.container, new TickListFragment(), TICKS_SCREEN)
                    .commit();
        }
    }

    private void moveToSettings(OnNavigateAction action) {
        if (manager != null) {
            if (action != null) {
                action.beforeNavigate();
            }
            if (state.equals(ScreenState.HISTORY)) {
                manager.popBackStack();
            }
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                    .addToBackStack(ScreenState.SETTINGS.toString())
                    .replace(R.id.container, new ToolSettingsFragment())
                    .commit();
        }
    }

    private enum ScreenState {
        TICKS,
        SETTINGS,
        HISTORY
    }
}
