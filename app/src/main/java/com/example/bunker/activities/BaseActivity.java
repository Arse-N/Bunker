package com.example.bunker.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bunker.R;
import com.example.bunker.service.GithubManager;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageView backButton, settingsButton, rulesButton;
    protected TextView headerTitle;

    private Dialog leaveGameDialog;

    private boolean yesPressed = false;

    private GithubManager githubManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        hideSystemUI();
        backButton = findViewById(R.id.back);
        headerTitle = findViewById(R.id.header_title);
        rulesButton = findViewById(R.id.rules);
        settingsButton = findViewById(R.id.settings);
        githubManager = new GithubManager(this);
        if (backButton != null) {
            backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
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
                    finish();
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

    @Override
    public void onBackPressed() {
        if (getContentViewId() == R.layout.activity_card_flip || getContentViewId() == R.layout.activity_game) {
            if (!yesPressed) {
                initializeDialog();
            } else {
                yesPressed = !yesPressed;
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
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

    public void initializeDialog() {
        leaveGameDialog = new Dialog(this);
        leaveGameDialog.setContentView(R.layout.leave_game_dialog);
        leaveGameDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        leaveGameDialog.setCancelable(false);
        leaveGameDialog.show();

        final Button yesButton = leaveGameDialog.findViewById(R.id.yes);
        final Button noButton = leaveGameDialog.findViewById(R.id.no);
        noButton.setBackgroundResource(R.drawable.button_background);
        yesButton.setBackgroundResource(R.drawable.button_background);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesPressed = true;
                onBackPressed();
                githubManager.deleteJsonFromGitHub();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveGameDialog.dismiss();
            }
        });


    }
}
