package com.example.bunker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.R;
import com.example.bunker.model.Teammate;
import com.example.bunker.util.JsonUtil;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Objects;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.PersonViewHolder> {
    private ArrayList<Teammate> teammatesList;
    private OnItemRemoveListener onItemRemoveListener;

    private Context context;

    public TeamAdapter(Context context, ArrayList<Teammate> teammatesList, OnItemRemoveListener onItemRemoveListener) {
        this.teammatesList = teammatesList;
        this.onItemRemoveListener = onItemRemoveListener;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Teammate teammate = teammatesList.get(position);
        holder.teammateItem.setBackgroundResource(R.drawable.item_background);

        if (holder.textWatcher != null) {
            holder.nameTextView.removeTextChangedListener(holder.textWatcher);
        }

        if (Objects.equals(teammate.getName(), "")) {
            holder.nameTextView.setText("");
            holder.nameTextView.setHint("Մասնակից " + (position + 1));
        } else {
            holder.nameTextView.setText(teammate.getName());
        }

        if (teammatesList.size() > 4) {
            holder.removeButton.setVisibility(View.VISIBLE);
        } else {
            holder.removeButton.setVisibility(View.GONE);
        }

        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                teammate.setName(s.toString());
                JsonUtil.writeToPlayersJson(context, teammatesList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        holder.nameTextView.addTextChangedListener(holder.textWatcher);

        holder.removeButton.setOnClickListener(v -> {
            holder.itemView.animate()
                    .translationX(holder.itemView.getWidth())
                    .alpha(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(300)
                    .withEndAction(() -> {
                        onItemRemoveListener.onItemRemove(position);
                        holder.itemView.setTranslationX(0);
                        holder.itemView.setAlpha(1);
                    })
                    .start();
        });



    }

    @Override
    public int getItemCount() {
        return teammatesList.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout teammateItem;
        TextInputEditText nameTextView;
        ImageView removeButton;
        TextWatcher textWatcher;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            teammateItem = itemView.findViewById(R.id.teammate_item);
            nameTextView = itemView.findViewById(R.id.name_text);
            removeButton = itemView.findViewById(R.id.delete);
        }
    }

    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }
}
