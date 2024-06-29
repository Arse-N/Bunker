package com.example.bunker;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.text.HtmlCompat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private TextView title, description, title_first_letter;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.story_title);
        title_first_letter = findViewById(R.id.story_first_letter);
        image = findViewById(R.id.story_image);
        description = findViewById(R.id.story);
        loadJson();
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
            int randomNumber = random.nextInt(jsonArray.length()-3);
            JSONObject item = jsonArray.getJSONObject(randomNumber);
            title.setText(item.getString("title"));
            setImageFromResource(item.getString("imageUrl"));
            title_first_letter.setText(item.getString("first_letter"));
            description.setText(HtmlCompat.fromHtml(item.getString("description"), HtmlCompat.FROM_HTML_MODE_LEGACY));

        } catch (Exception e) {
            Log.e("TAG", "LoadJson: error" + e);
        }
    }

    private void setImageFromResource(String resourceName) {
        // Extract the resource name from the JSON value, e.g., @drawable/image_name
        if (resourceName.startsWith("@drawable/")) {
            String resName = resourceName.split("/")[1];
            int resId = getResources().getIdentifier(resName, "drawable", getPackageName());
            if (resId != 0) {
                image.setImageResource(resId);
            } else {
                Log.e("TAG", "Resource not found: " + resourceName);
            }
        } else {
            Log.e("TAG", "Invalid resource name format: " + resourceName);
        }
    }
}