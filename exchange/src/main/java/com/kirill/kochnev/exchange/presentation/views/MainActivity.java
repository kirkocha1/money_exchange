package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.repositories.TickRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Inject
    TickRepository repository;

    @BindView(R.id.btn_1)
    Button btn;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.reconnect)
    Button reconnect;


    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExchangeApplication.getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btn.setOnClickListener(v -> {
            repository.sendMessage("UNSUBSCRIBE: EURUSD,EURGBP").subscribe(() -> {
            }, e -> Log.e("ACTIVITY", e.getMessage()));
        });
        send.setOnClickListener(v -> {
            repository.sendMessage("SUBSCRIBE: EURUSD,EURGBP").subscribe(() -> {
            }, e -> Log.e("ACTIVITY", e.getMessage()));
        });
        reconnect.setOnClickListener(v -> {
            sub();
        });
    }

    private void sub() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = repository.getTicks().observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (list != null) {
                        Log.e("ACTIVITY", "LIST COUNT: " + list.size());
                        for (TickDb tick : list) {
                            Log.d("ACTIVITY", "TICK ASK: " + tick.getAsk() + " TICK bid: " + tick.getBid() + " TICK TOOL TYPE: " + tick.getToolType());
                        }
                    }
                }, e -> {
                    Log.e("ACTIVITY", "ERROR MESSAGE: " + e.getMessage());
                });
    }
}
