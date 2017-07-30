package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.domain.models.TickUI;
import com.kirill.kochnev.exchange.presentation.interfaces.ITickListView;
import com.kirill.kochnev.exchange.presentation.presenters.TickListPresenter;
import com.kirill.kochnev.exchange.presentation.views.adapter.TicksAdapter;
import com.kirill.kochnev.exchange.presentation.views.components.ListWithBlankView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TickListFragment extends MvpAppCompatFragment implements ITickListView {

    public static final String TAG = "TickListFragment";

    @BindView(R.id.list_view)
    ListWithBlankView list;

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
        list.initList(adapter);
    }

    @Override
    public void invalidateList(List<TickUI> list) {
        this.list.post(() -> {
            Log.e(TAG, "invalidateList " + list.size());
            adapter.updateData(list);
        });
    }

    @Override
    public void recreateList(List<TickUI> ticks) {
        adapter.replceWithNewList(ticks);
        list.post(() -> {
            list.setBlankVisibility(ticks == null);
        });
    }
}
