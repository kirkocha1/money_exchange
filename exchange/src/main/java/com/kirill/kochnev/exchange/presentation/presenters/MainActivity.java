package com.kirill.kochnev.exchange.presentation.presenters;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.websocket.RxSocketWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private RxSocketWrapper socket;

    @BindView(R.id.btn_1)
    Button btn;
    @BindView(R.id.send)
    Button send;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        btn.setOnClickListener(v -> {
            socket.disconnect();
            disposable.dispose();
            new Handler().postDelayed(() -> sub(), 5000);
        });
        send.setOnClickListener(v -> {

            socket.sendMessage("SUBSCRIBE: EURUSD,EURGBP");

        });
        socket = new RxSocketWrapper("wss://quotes.exness.com:18400/");
        sub();

    }

    private void sub() {
        disposable = socket.getSocketObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    Log.e("FFFF", message);
                }, e -> {
                    Log.e("fffff", e.getMessage());
                }, () -> {
                    Log.e("FFFF", "ONCOMPLETE");
                });
    }
}
