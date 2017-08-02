package com.kirill.kochnev.exchange.presentation.views.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kirill.kochnev.exchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 02.08.17.
 */

public class TickHeaderView extends FrameLayout {

    @BindView(R.id.bid_container)
    LinearLayout bidContainer;

    @BindView(R.id.ask_container)
    LinearLayout askContainer;

    @BindView(R.id.tool_container)
    LinearLayout toolContainer;

    @BindView(R.id.spread_container)
    LinearLayout spreadContainer;

    @BindView(R.id.bid_sort)
    SortItemView bidSort;

    @BindView(R.id.ask_sort)
    SortItemView askSort;

    @BindView(R.id.tool_sort)
    SortItemView toolSort;

    @BindView(R.id.spread_sort)
    SortItemView spreadSort;

    private OnSortOrderListener toolSortListener;
    private OnSortOrderListener bidSortListener;
    private OnSortOrderListener askSortListener;
    private OnSortOrderListener spreadSortListener;

    public TickHeaderView(@NonNull Context context) {
        super(context);
        initUi(context);
    }

    public TickHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUi(context);
    }

    private void initUi(Context context) {
        LayoutInflater.from(context).inflate(R.layout.header_list, this, true);
        ButterKnife.bind(this);
        toolContainer.setOnClickListener(v -> {
            setToolSortOrder(!toolSort.isAsk());
            if (toolSortListener != null) {
                toolSortListener.onChange(v, toolSort.isAsk());
            }
        });
        askContainer.setOnClickListener(v -> {
            setAskSortOrder(!askSort.isAsk());
            if (askSortListener != null) {
                askSortListener.onChange(v, askSort.isAsk());
            }
        });

        bidContainer.setOnClickListener(v -> {
            setBidSortOrder(!bidSort.isAsk());
            if (bidSortListener != null) {
                bidSortListener.onChange(v, bidSort.isAsk());
            }
        });

        spreadContainer.setOnClickListener(v -> {
            setSpreadSortOrder(!spreadSort.isAsk());
            if (spreadSortListener != null) {
                spreadSortListener.onChange(v, spreadSort.isAsk());
            }
        });
    }

    public void setOnSpreadSortClickListner(OnSortOrderListener listner) {
        spreadSortListener = listner;
    }

    public void setOnToolSortClickListner(OnSortOrderListener listner) {
        toolSortListener = listner;
    }

    public void setBidSortListener(OnSortOrderListener bidSortListener) {
        this.bidSortListener = bidSortListener;
    }

    public void setAskSortListener(OnSortOrderListener askSortListener) {
        this.askSortListener = askSortListener;
    }

    private void setSpreadSortOrder(boolean isAsk) {
        bidSort.setVisibility(GONE);
        askSort.setVisibility(GONE);
        toolSort.setVisibility(GONE);
        spreadSort.setVisibility(VISIBLE);
        spreadSort.setSortOrder(isAsk);
    }

    private void setBidSortOrder(boolean isAsk) {
        spreadSort.setVisibility(GONE);
        askSort.setVisibility(GONE);
        toolSort.setVisibility(GONE);
        bidSort.setVisibility(VISIBLE);
        bidSort.setSortOrder(isAsk);
    }

    private void setAskSortOrder(boolean isAsk) {
        bidSort.setVisibility(GONE);
        spreadSort.setVisibility(GONE);
        toolSort.setVisibility(GONE);
        askSort.setVisibility(VISIBLE);
        askSort.setSortOrder(isAsk);
    }

    private void setToolSortOrder(boolean isAsk) {
        spreadSort.setVisibility(GONE);
        bidSort.setVisibility(GONE);
        askSort.setVisibility(GONE);
        toolSort.setVisibility(VISIBLE);
        toolSort.setSortOrder(isAsk);
    }

    public interface OnSortOrderListener {
        void onChange(View v, boolean isAsk);
    }

}
