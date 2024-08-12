package com.example.bunker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView backButton;
    protected TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        backButton = findViewById(R.id.back);
        headerTitle = findViewById(R.id.header_title);

        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBackNavigation();
                }
            });
        }
    }

    private void handleBackNavigation() {
        if (shouldCloseApp()) {
            finish(); // Close the activity if there's no previous activity
        } else {
            onBackPressed(); // Navigate to the previous activity
        }
    }

    private boolean shouldCloseApp() {
        // Check if there's no previous activity in the back stack
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    protected abstract int getContentViewId();
}
