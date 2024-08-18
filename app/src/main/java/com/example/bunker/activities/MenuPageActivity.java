package com.example.bunker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.bunker.R;
import com.example.bunker.model.GameInfo;
import com.example.bunker.util.JsonUtil;

public class MenuPageActivity extends FullScreenActivity {

    private Button startButton;
    private Button howToPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startButton = findViewById(R.id.start);
        howToPlayButton = findViewById(R.id.how_to_play);
        startButton.setBackgroundResource(R.drawable.button_background);
        howToPlayButton.setBackgroundResource(R.drawable.button_background);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPageActivity.this, TeamActivity.class);
                GameInfo gameInfo = JsonUtil.readFromGameInfoJson(MenuPageActivity.this);
                if (gameInfo == null) {
                    gameInfo = new GameInfo();
                    JsonUtil.writeToGameInfoJson(MenuPageActivity.this, gameInfo);
                }
                startActivity(intent);
            }
        });
        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPageActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_menu_page;
    }
}