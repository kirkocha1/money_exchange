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
import com.kirill.kochnev.exchange.presentation.utils.ErrorHandler;
import com.kirill.kochnev.exchange.presentation.utils.FragmentNavigator;
import com.kirill.kochnev.exchange.presentation.utils.TickComparatorFactory;
import com.kirill.kochnev.exchange.presentation.utils.TickTimer;
import com.kirill.kochnev.exchange.presentation.views.adapter.TicksAdapter;
import com.kirill.kochnev.exchange.presentation.views.components.ListWithBlankView;
import com.kirill.kochnev.exchange.presentation.views.components.TickHeaderView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TickListFragment extends MvpAppCompatFragment implements ITickListView {

    public static final String TAG = "TickListFragment";

    private TicksAdapter adapter;

    @BindView(R.id.list_view)
    ListWithBlankView list;

    @Inject
    TickInteractor interactor;

    @Inject
    FragmentNavigator navigator;

    @Inject
    TickTimer tickTimer;

    @InjectPresenter
    TickListPresenter presenter;

    @ProvidePresenter
    TickListPresenter providePresenter() {
        return new TickListPresenter(interactor, tickTimer);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ExchangeApplication.getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigator.setManager(getFragmentManager());
    }

    @Override
    public void onPause() {
        super.onPause();
        navigator.setManager(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tick_list, container, false);
        init(v);
        return v;
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
        adapter.replaceWithNewList(ticks);
        list.post(() -> {
            list.setBlankVisibility(ticks == null);
        });
    }

    @Override
    public void showMessage(String error) {
        new ErrorHandler().showSnackBar(list, error, v -> {
            presenter.retry();
        });
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        TickHeaderView header = new TickHeaderView(getActivity());
        header.setOnSpreadSortClickListner((v, isAsk) -> {
            adapter.changeListOrder(TickComparatorFactory.create(TickComparatorFactory.SPREAD_COMPARATOR, isAsk));
        });
        header.setAskSortListener((v, isAsk) -> {
            adapter.changeListOrder(TickComparatorFactory.create(TickComparatorFactory.ASK_COMPARATOR, isAsk));
        });
        header.setBidSortListener((v, isAsk) -> {
            adapter.changeListOrder(TickComparatorFactory.create(TickComparatorFactory.BID_COMPARATOR, isAsk));
        });
        header.setOnToolSortClickListner((v, isAsk) -> {
            adapter.changeListOrder(TickComparatorFactory.create(TickComparatorFactory.DEFAULT, isAsk));
        });
        adapter = new TicksAdapter();
        adapter.setListener( (v, tool) -> {
            navigator.navigateTo(FragmentNavigator.TICK_HISITORY_SCREEN, tool, null);
        });
        list.setHeader(header);
        list.initList(adapter);
    }

}
