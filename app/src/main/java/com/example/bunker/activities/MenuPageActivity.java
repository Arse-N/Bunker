package com.example.bunker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.bunker.R;
import com.example.bunker.model.GameInfo;
import com.example.bunker.util.JsonUtil;

public class MenuPageActivity extends BaseActivity {

    private Button startButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startButton = findViewById(R.id.start);
        exitButton = findViewById(R.id.exit);
        startButton.setBackgroundResource(R.drawable.button_background);
        exitButton.setBackgroundResource(R.drawable.button_background);
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
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_menu_page;
    }
}