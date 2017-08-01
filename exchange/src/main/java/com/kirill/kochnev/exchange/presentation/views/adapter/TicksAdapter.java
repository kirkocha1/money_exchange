package com.kirill.kochnev.exchange.presentation.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class TicksAdapter extends RecyclerView.Adapter<TicksAdapter.TickViewHolder> {

    private List<TickUI> models;

    public void replceWithNewList(List<TickUI> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void updateData(List<TickUI> models) {
        replaceWithLatest(this.models, models);
        notifyDataSetChanged();
    }

    private void replaceWithLatest(List<TickUI> oldList, List<TickUI> newList) {
        if (oldList != null && newList != null) {
            for (TickUI tickUI : oldList) {
                for (TickUI newTick : newList) {
                    if (tickUI.getType() == newTick.getType()) {
                        tickUI.updateWithAnother(newTick);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public TickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TickViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tick_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(TickViewHolder holder, int position) {
        if (models != null && !models.isEmpty()) {
            holder.pair.setText(models.get(position).getType().getSlashName());
            holder.spread.setText(models.get(position).getSpread() + "");
            holder.relation.setText(models.get(position).getBidAndAskFormated());
        }
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    class TickViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.exchange_pair)
        TextView pair;

        @BindView(R.id.spread)
        TextView spread;

        @BindView(R.id.money_relation)
        TextView relation;

        public TickViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
