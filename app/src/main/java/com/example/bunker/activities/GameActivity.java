package com.example.bunker.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import com.example.bunker.R;
import com.example.bunker.fragments.CardFlipFragment;
import com.example.bunker.fragments.GameFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GameActivity extends BaseActivity {

    private TextView title, description, roundHeader, roundDesc;
    private ImageView storyButton;

    private Button nextButton;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private boolean isArrowUp = true;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_game, new GameFragment())
                    .commit();
        }

        View bottomSheet = findViewById(R.id.story_pop_up);
        storyButton = findViewById(R.id.story_button);
        title = findViewById(R.id.story_header);
        description = findViewById(R.id.story);
        ScrollView storyScrollView = findViewById(R.id.story_scroll_view);


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setDraggable(true);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        animateArrow(false);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        animateArrow(true);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        storyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });

        storyScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        bottomSheetBehavior.setDraggable(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        bottomSheetBehavior.setDraggable(true);
                        break;
                }
                return false;
            }
        });

        loadJson();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_game;
    }

    private void loadJson() {
        try {
            Random random = new Random();
            InputStream inputStream = getAssets().open("stories.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            int randomNumber = random.nextInt(jsonArray.length() - 3);
            JSONObject item = jsonArray.getJSONObject(randomNumber);
            title.setText(item.getString("title"));
            String story = String.valueOf(HtmlCompat.fromHtml(item.getString("description"), HtmlCompat.FROM_HTML_MODE_LEGACY));
            description.setText(story);

        } catch (Exception e) {
            Log.e("TAG", "LoadJson: error" + e);
        }
    }

    private void toggleBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void animateArrow(boolean pointingUp) {
        float startRotation = isArrowUp ? 0f : 180f;
        float endRotation = pointingUp ? 0f : 180f;
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(storyButton, "rotation", startRotation, endRotation);
        rotateAnimation.setDuration(200);
        rotateAnimation.start();
        isArrowUp = pointingUp;
    }
}
