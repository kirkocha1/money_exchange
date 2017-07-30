package com.kirill.kochnev.exchange.presentation.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.exchange.ExchangeApplication;
import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.interactors.TickInteractor;
import com.kirill.kochnev.exchange.presentation.interfaces.IToolSettingsView;
import com.kirill.kochnev.exchange.presentation.presenters.ToolSettingsPresenter;
import com.kirill.kochnev.exchange.presentation.views.adapter.ToolsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class ToolSettingsFragment extends MvpFragment implements IToolSettingsView {

    private static final String TAG = "ToolSettingsFragment";

    @Inject
    TickInteractor interactor;

    @InjectPresenter
    ToolSettingsPresenter presenter;

    @ProvidePresenter
    ToolSettingsPresenter providePresenter() {
        return new ToolSettingsPresenter(interactor);
    }

    @BindView(R.id.list)
    RecyclerView list;

    ToolsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExchangeApplication.getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tool_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new ToolsAdapter();
        adapter.setListener(presenter::changeToolTypeModification);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
    }

    @Override
    public void invalidateToolTypeList(List<ToolType> toolTypes) {
        adapter.setCheckedItems(toolTypes);
    }
}
