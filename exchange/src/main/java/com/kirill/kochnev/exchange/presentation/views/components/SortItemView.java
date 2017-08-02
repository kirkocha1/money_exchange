package com.kirill.kochnev.exchange.presentation.views.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kirill.kochnev.exchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 02.08.17.
 */

public class SortItemView extends FrameLayout {

    @BindView(R.id.desc)
    ImageView descImage;

    @BindView(R.id.asc)
    ImageView ascImage;

    private boolean isAsk = true;

    public SortItemView(@NonNull Context context) {
        super(context);
        initUi(context);
    }

    public SortItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUi(context);
    }

    private void initUi(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sort_item, this, true);
        ButterKnife.bind(this);
    }

    public void setSortOrder(boolean isAsc) {
        this.isAsk = isAsc;
        descImage.setVisibility(isAsc ? GONE : VISIBLE);
        ascImage.setVisibility(isAsc ? VISIBLE : GONE);
    }

    public boolean isAsk() {
        return isAsk;
    }
}
