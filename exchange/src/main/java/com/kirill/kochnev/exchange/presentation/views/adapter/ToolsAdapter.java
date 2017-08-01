package com.kirill.kochnev.exchange.presentation.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kirill.kochnev.exchange.R;
import com.kirill.kochnev.exchange.data.enums.ToolType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ToolViewHolder> {

    private OnCheckBoxListener listener;
    private List<ToolType> checkedItems = new ArrayList<>();

    public void setCheckedItems(List<ToolType> checkedItems) {
        this.checkedItems = checkedItems;
        notifyDataSetChanged();
    }

    @Override
    public ToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ToolViewHolder holder, int position) {
        holder.name.setText(ToolType.values()[position].getSlashName());
        holder.checkBox.setChecked(isChecked(ToolType.values()[position]));
        holder.checkBox.setOnClickListener(view -> {
            if (listener != null) {
                boolean isCheckedBeforeClick = isChecked(ToolType.values()[position]);
                if (!isCheckedBeforeClick) {
                    checkedItems.add(ToolType.values()[position]);
                } else {
                    removeFromList(ToolType.values()[position]);
                }
                listener.onChange(!isCheckedBeforeClick, ToolType.values()[position]);
            }
        });
    }

    private void removeFromList(ToolType toolType) {
        for (ToolType type : checkedItems) {
            if (type.equals(toolType)) {
                checkedItems.remove(type);
                return;
            }
        }
    }

    private boolean isChecked(ToolType toolType) {
        for (ToolType type : checkedItems) {
            if (type.equals(toolType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return ToolType.values().length;
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tool_name)
        TextView name;

        @BindView(R.id.check)
        CheckBox checkBox;

        public ToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void uncheckTool(ToolType type) {
        boolean wasAdded = false;
        for (ToolType item : checkedItems) {
            if (item.equals(type)) {
                checkedItems.remove(item);
                wasAdded = true;
                break;
            }
        }
        if (!wasAdded) {
            checkedItems.add(type);
        }
        notifyDataSetChanged();
    }

    public void setListener(OnCheckBoxListener listener) {
        this.listener = listener;
    }

    public interface OnCheckBoxListener {
        void onChange(boolean isChecked, ToolType type);
    }
}


