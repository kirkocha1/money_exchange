package com.kirill.kochnev.exchange.presentation.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.presentation.interfaces.IRetry;

/**
 * Created by kirill on 31.07.17.
 */

public class ErrorHandler {

    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.red));
        snackbar.show();
    }

    public static void showSnackBar(View view, String message, IRetry listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.red));
        snackbar.setAction(R.string.retry, v -> {
            if (listener != null) {
                listener.retry();
            }
        });
        snackbar.show();

    }
}
