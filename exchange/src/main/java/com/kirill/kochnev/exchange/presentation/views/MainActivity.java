package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.presentation.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.back_action)
    ImageView back;


    @BindView(R.id.settings)
    ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExchangeApplication.getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        back.setOnClickListener(v -> {
            getSupportFragmentManager().popBackStack();
            AnimationHelper.rotateHideAnimation(this, back, true);

        });
        settings.setOnClickListener(v -> {
            if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
                AnimationHelper.rotateHideAnimation(this, back, false);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                        .addToBackStack("Settings")
                        .replace(R.id.container, new ToolSettingsFragment())
                        .commit();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_right)
                .replace(R.id.container, new TickListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (back.getVisibility() == View.VISIBLE) {
            AnimationHelper.rotateHideAnimation(this, back, true);
        }
        super.onBackPressed();
    }
}
