package com.example.bunker.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bunker.R;
import com.example.bunker.fragments.CardFlipFragment;

public class CardFlipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_card_flip, new CardFlipFragment())
                    .commit();
        }
    }
}
