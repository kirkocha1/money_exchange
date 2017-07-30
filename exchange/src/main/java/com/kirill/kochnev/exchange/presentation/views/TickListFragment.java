package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.domain.models.TickUI;
import com.kirill.kochnev.exchange.presentation.interfaces.ITickListView;
import com.kirill.kochnev.exchange.presentation.presenters.TickListPresenter;
import com.kirill.kochnev.exchange.presentation.views.adapter.TicksAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TickListFragment extends MvpFragment implements ITickListView {

    public static final String TAG = "TickListFragment";

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.blank)
    ImageView blank;

    @Inject
    TickInteractor interactor;

    @InjectPresenter
    TickListPresenter presenter;

    @ProvidePresenter
    TickListPresenter providePresenter() {
        return new TickListPresenter(interactor);
    }

    private TicksAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExchangeApplication.getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tick_list, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new TicksAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void invalidateList(List<TickUI> list) {
        this.list.post(() -> {
            Log.e(TAG, "invalidateList " + list.size());
            adapter.updateData(list);
        });
    }

    @Override
    public void recreateList(List<TickUI> list) {
        adapter.replceWithNewList(list);
        this.list.post(() -> {
            this.list.setVisibility(list == null ? View.GONE : View.VISIBLE);
            blank.setVisibility(list == null ? View.VISIBLE : View.GONE);
        });
    }
}
