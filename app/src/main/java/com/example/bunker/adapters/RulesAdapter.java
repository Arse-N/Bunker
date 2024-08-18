package com.example.bunker.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.R;
import com.example.bunker.model.Rule;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.RulesViewHolder> {

    private final Rule[] rules;
    public RulesAdapter(Rule[] rules) {
        this.rules = rules;
    }

    @NonNull
    @Override
    public RulesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rule_item, parent, false);
        return new RulesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RulesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Rule rule = rules[position];
        holder.header.setText(rule.getHeader());
        holder.desc.setText(rule.getDescription());
    }

    @Override
    public int getItemCount() {
        return rules.length;
    }

    static class RulesViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout ruleItem;
        TextView header, desc;

        public RulesViewHolder(@NonNull View itemView) {
            super(itemView);
            ruleItem = itemView.findViewById(R.id.rule_item);
            header = itemView.findViewById(R.id.rule_header);
            desc = itemView.findViewById(R.id.rule_desc);
        }
    }
}
