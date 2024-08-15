package com.example.bunker.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bunker.R;

public class MenuPageActivity extends AppCompatActivity {

    private Button startButton;
    private Button howToPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        startButton = findViewById(R.id.start);
        howToPlayButton = findViewById(R.id.how_to_play);
        startButton.setBackgroundResource(R.drawable.button_background);
        howToPlayButton.setBackgroundResource(R.drawable.button_background);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPageActivity.this, TeamActivity.class);
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
}