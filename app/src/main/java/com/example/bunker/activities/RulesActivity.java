package com.example.bunker.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.R;
import com.example.bunker.adapters.RulesAdapter;
import com.example.bunker.constants.RulesData;

public class RulesActivity extends BaseActivity {

    private ImageButton rulesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rulesButton = findViewById(R.id.rules);
        rulesButton.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.rules_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RulesAdapter adapter = new RulesAdapter(RulesData.rules);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rules;
    }

}
