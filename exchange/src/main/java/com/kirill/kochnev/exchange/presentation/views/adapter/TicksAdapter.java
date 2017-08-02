package com.kirill.kochnev.exchange.presentation.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.domain.models.TickUI;
import com.kirill.kochnev.exchange.presentation.utils.TickComparatorFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TicksAdapter extends RecyclerView.Adapter<TicksAdapter.TickViewHolder> {

    private TreeSet<TickUI> models;
    private Comparator<TickUI> comparator;

    public void replaceWithNewList(List<TickUI> models) {
        if (comparator == null) {
            comparator = TickComparatorFactory.create(TickComparatorFactory.DEFAULT, false);
        }
        this.models = new TreeSet<>(comparator);
        if (models != null) {
            this.models.addAll(models);
            notifyDataSetChanged();
        }
    }

    public void updateData(List<TickUI> models) {
        if (this.models == null) {
            this.models = new TreeSet<>(comparator);
        }
        replaceWithLatest(this.models, models);
        notifyDataSetChanged();
    }

    public void changeListOrder(Comparator<TickUI> comparator) {
        this.comparator = comparator;
        TreeSet<TickUI> treeSet = new TreeSet<>(this.comparator);
        treeSet.addAll(models);
        models = treeSet;
        notifyDataSetChanged();
    }

    @Override
    public TickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TickViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tick_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(TickViewHolder holder, int position) {
        if (models != null && !models.isEmpty()) {
            TickUI tickUI = findByPosition(position);
            if (tickUI != null) {
                holder.pair.setText(tickUI.getType().getSlashName());
                holder.spread.setText(tickUI.getSpread() + "");
                holder.relation.setText(tickUI.getBidAndAskFormated());
            }
        }
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    private void replaceWithLatest(TreeSet<TickUI> treeSet, List<TickUI> newList) {
        if (treeSet != null && newList != null) {
            List<TickUI> newElements = new ArrayList<>();
            Iterator<TickUI> iterator = treeSet.iterator();
            while (iterator.hasNext()) {
                TickUI tickUI = iterator.next();
                for (TickUI newTick : newList) {
                    if (tickUI.getType() == newTick.getType()) {
                        iterator.remove();
                        newElements.add(newTick);
                        break;
                    }
                }
            }
            treeSet.addAll(newElements);
        }
    }

    private TickUI findByPosition(int position) {
        TickUI result = null;
        Iterator<TickUI> iterator = this.models.iterator();
        while (position >= 0 && iterator.hasNext()) {
            result = iterator.next();
            position--;
        }
        return result;
    }

    class TickViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.exchange_pair)
        TextView pair;

        @BindView(R.id.spread)
        TextView spread;

        @BindView(R.id.money_relation)
        TextView relation;

        TickViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
