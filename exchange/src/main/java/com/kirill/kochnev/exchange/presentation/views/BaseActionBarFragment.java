package com.kirill.kochnev.exchange.presentation.views;

import android.content.Context;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.kirill.kochnev.exchange.presentation.interfaces.IActionBarController;

/**
 * Created by kirill on 04.08.17.
 */

public class BaseActionBarFragment extends MvpAppCompatFragment implements IActionBarController {

    private IActionBarController controller;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IActionBarController) {
            controller = (IActionBarController) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        controller = null;
    }

    @Override
    public void setTitle(String title) {
        controller.setTitle(title);
    }

    @Override
    public void hideBackButton(boolean isBack) {
        controller.hideBackButton(isBack);
    }

    public IActionBarController getController() {
        return controller;
    }
}
