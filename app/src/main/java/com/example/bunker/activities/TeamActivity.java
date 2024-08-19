package com.example.bunker.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.R;
import com.example.bunker.adapters.TeamAdapter;
import com.example.bunker.model.Teammate;
import com.example.bunker.util.JsonUtil;

import java.util.ArrayList;

public class TeamActivity extends BaseActivity implements TeamAdapter.OnItemRemoveListener {

    private RecyclerView recyclerView;

    private Button add, start;
    private ArrayList<Teammate> teammatesList;
    private TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        teammatesList = JsonUtil.readFromPlayersJson(this);
        teamAdapter = new TeamAdapter(this, teammatesList, this);

        recyclerView = findViewById(R.id.teammates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(teamAdapter);

        add = findViewById(R.id.add_button);
        start = findViewById(R.id.start);
        add.setBackgroundResource(R.drawable.add_background);
        start.setBackgroundResource(R.drawable.button_background);
        checkTeammateItemsCount();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeammate();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teammatesList.size() >= 4) {
                    Intent intent = new Intent(TeamActivity.this, CardFlipActivity.class);
                    startActivity(intent);
                } else {
                    showToast("minimum number of players is 4!");
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_team;

    }

    private void addTeammate() {
        teammatesList.add(new Teammate(""));
        teamAdapter.notifyItemInserted(teammatesList.size() - 1);

        if (teammatesList.size() == 5) {
            teamAdapter.notifyDataSetChanged();
        }

        JsonUtil.writeToPlayersJson(this, teammatesList);
    }

    private void checkTeammateItemsCount() {
        if (teammatesList.isEmpty()) {
            for (int i = 1; i <= 4; i++) {
                teammatesList.add(new Teammate(""));
            }
        }
    }

    @Override
    public void onItemRemove(int position) {
        teammatesList.remove(position);
        teamAdapter.notifyItemRemoved(position);
        teamAdapter.notifyDataSetChanged();
        JsonUtil.writeToPlayersJson(this, teammatesList);
    }



    private void showToast(String text){
        Toast.makeText(TeamActivity.this, text, Toast.LENGTH_LONG).show();
    }

}