package com.example.bunker.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bunker.R;
import com.example.bunker.fragments.CardFlipFragment;

public class CardFlipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_card_flip, new CardFlipFragment())
                    .commit();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_card_flip;
    }
}
