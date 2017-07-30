package com.kirill.kochnev.exchange.presentation.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.kirill.kochnev.exchange.R;

/**
 * Created by Kirill Kochnev on 30.07.17.
 */

public class AnimationHelper {

    public static void rotateHideAnimation(Context context, View view, boolean isHide) {
        view.startAnimation(AnimationUtils.loadAnimation(context, isHide ? R.anim.fade_in_rotating : R.anim.fade_out_rotating));
        view.postOnAnimation(() -> {
            view.setVisibility(isHide ? View.GONE : View.VISIBLE);
        });

    }
}
