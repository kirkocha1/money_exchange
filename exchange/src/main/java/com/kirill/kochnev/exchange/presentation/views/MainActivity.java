package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.presentation.interfaces.IActionBarController;
import com.kirill.kochnev.exchange.presentation.utils.AnimationHelper;
import com.kirill.kochnev.exchange.presentation.utils.FragmentNavigator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IActionBarController {

    private boolean isStopOrSaved = true;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.back_action)
    ImageView back;

    @BindView(R.id.bar_title)
    TextView title;

    @BindView(R.id.settings)
    ImageView settings;

    @Inject
    FragmentNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isStopOrSaved = savedInstanceState != null;
        ExchangeApplication.getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        title.setText(getString(R.string.ticks_title));
        back.setOnClickListener(v -> navigator.back(() -> {
            AnimationHelper.rotateHideAnimation(this, back, true);
        }));
        settings.setOnClickListener(v -> navigator.navigateTo(FragmentNavigator.SETTINGS_SCREEN));
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigator.setManager(getSupportFragmentManager());
        if (!isStopOrSaved) {
            navigator.navigateTo(FragmentNavigator.TICKS_SCREEN);
            isStopOrSaved = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigator.clear();
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void hideBackButton(boolean isBack) {
        AnimationHelper.rotateHideAnimation(this, back, isBack);
    }

    @Override
    public void onBackPressed() {
        if (back.getVisibility() == View.VISIBLE) {
            AnimationHelper.rotateHideAnimation(this, back, true);
        }
        super.onBackPressed();
    }
}
