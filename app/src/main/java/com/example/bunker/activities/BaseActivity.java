package com.example.bunker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bunker.R;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView backButton, settingsButton, rulesButton;
    protected TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        hideSystemUI();
        backButton = findViewById(R.id.back);
        headerTitle = findViewById(R.id.header_title);
        rulesButton = findViewById(R.id.rules);
        settingsButton = findViewById(R.id.settings);

        if (backButton != null) {
            backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBackNavigation();
                }
            });
        }

        if (rulesButton != null) {
            rulesButton.setVisibility(View.VISIBLE);
            rulesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BaseActivity.this, RulesActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (settingsButton != null) {
            settingsButton.setVisibility(View.VISIBLE);
            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BaseActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void handleBackNavigation() {
        if (shouldCloseApp()) {
            finish();
        } else {
            onBackPressed();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    private boolean shouldCloseApp() {
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    protected abstract int getContentViewId();
}
