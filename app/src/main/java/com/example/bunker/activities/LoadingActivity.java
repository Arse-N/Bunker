package com.example.bunker.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import com.example.bunker.R;

public class LoadingActivity extends BaseActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, MenuPageActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_loading;
    }

}