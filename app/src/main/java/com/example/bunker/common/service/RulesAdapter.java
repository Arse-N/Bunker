package com.example.bunker.common.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.bunker.R;

public class RulesAdapter extends PagerAdapter {

    Context context;

    int[] images = {
            R.drawable.danger,
            R.drawable.danger,
            R.drawable.danger,
            R.drawable.danger,
            R.drawable.danger
    };

    int[] headings = {
            R.string.rule_one_header,
            R.string.rule_two_header,
            R.string.rule_three_header,
            R.string.rule_four_header,
            R.string.rule_five_header
    };

    int[] description = {

            R.string.rule_one_desc,
            R.string.rule_two_desc,
            R.string.rule_three_desc,
            R.string.rule_four_desc,
            R.string.rule_five_desc
    };

    public RulesAdapter(Context context) {

        this.context = context;

    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideTitleImage = view.findViewById(R.id.titleImage);
        TextView slideHeading = view.findViewById(R.id.texttitle);
        TextView slideDescription = view.findViewById(R.id.textdeccription);

        slideTitleImage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(description[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);

    }
}