package com.example.bunker.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.bunker.R;
import com.example.bunker.adapters.RulesAdapter;

public class RulesActivity extends BaseActivity {

    private ViewPager mSLideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] dots;
    private RulesAdapter rulesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSLideViewPager = findViewById(R.id.slide_view_pager);
        mDotLayout = findViewById(R.id.indicator_layout);
        headerTitle.setText(R.string.game_rules);

        rulesAdapter = new RulesAdapter(this);
        mSLideViewPager.setAdapter(rulesAdapter);

        setUpIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rules;
    }

    public void setUpIndicator(int position) {
        dots = new TextView[5];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private int getItem(int i) {
        return mSLideViewPager.getCurrentItem() + i;
    }
}
