package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        settings.setOnClickListener(v ->
                getFragmentManager().beginTransaction().addToBackStack("Settings")
                        .replace(R.id.container, new ToolSettingsFragment())
                        .commit());
        getFragmentManager().beginTransaction()
                .addToBackStack("start")
                .replace(R.id.container, new TickListFragment())
                .commit();
    }

}
