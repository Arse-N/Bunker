package com.example.bunker.common.service;// PersonAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.R;
import com.example.bunker.common.model.Teammate;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.PersonViewHolder> {
    private ArrayList<Teammate> personList;
    private OnItemRemoveListener onItemRemoveListener;

    public TeamAdapter(ArrayList<Teammate> personList, OnItemRemoveListener onItemRemoveListener) {
        this.personList = personList;
        this.onItemRemoveListener = onItemRemoveListener;

    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Teammate person = personList.get(position);
        holder.teammateItem.setBackgroundResource(R.drawable.button_background);
        holder.nameTextView.setText(person.getName());
//        holder.emailTextView.setText(person.getEmail());
        holder.removeButton.setOnClickListener(v -> {
            onItemRemoveListener.onItemRemove(position);

        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout teammateItem;
        TextView nameTextView;
//        TextView emailTextView;
        ImageView removeButton;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            teammateItem = itemView.findViewById(R.id.teammate_item);
            nameTextView = itemView.findViewById(R.id.name_value);
//            emailTextView = itemView.findViewById(R.id.email_value);
            removeButton = itemView.findViewById(R.id.delete);
        }
    }

    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }
}
