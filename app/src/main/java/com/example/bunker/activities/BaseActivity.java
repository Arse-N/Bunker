package com.example.bunker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bunker.R;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView backButton, settingsButton;
    protected TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        backButton = findViewById(R.id.back);
        headerTitle = findViewById(R.id.header_title);
        settingsButton = findViewById(R.id.settings);

        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBackNavigation();
                }
            });
        }

        if (settingsButton != null) {
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

    private boolean shouldCloseApp() {
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    protected abstract int getContentViewId();
}
