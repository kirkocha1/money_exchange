package com.kirill.kochnev.exchange.presentation.views.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kirill.kochnev.exchange.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 30.07.17.
 */

/**
 * List UI view with empty list picture
 */
public class ListWithBlankView extends FrameLayout {

    @BindView(R.id.list_header)
    FrameLayout header;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.blank)
    LinearLayout blank;


    public ListWithBlankView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ListWithBlankView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setBlankVisibility(boolean isBlankVisible) {
        blank.setVisibility(isBlankVisible ? VISIBLE : GONE);
        list.setVisibility(isBlankVisible ? View.GONE : View.VISIBLE);
    }

    public void initList(RecyclerView.Adapter adapter) {
        list.setAdapter(adapter);
    }

    public void setHeader(View view) {
        header.addView(view);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_view, this, true);
        ButterKnife.bind(this);
        list.setLayoutManager(new LinearLayoutManager(context));
    }
}
