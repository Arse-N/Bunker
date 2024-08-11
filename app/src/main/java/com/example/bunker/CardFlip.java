package com.example.bunker;


import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.example.bunker.common.constants.Information;
import com.example.bunker.common.fileio.JsonUtil;
import com.example.bunker.common.model.Teammate;
import com.example.bunker.common.service.SendGridEmailSender;

import java.util.*;

public class CardFlip extends AppCompatActivity {

    private CardView cardBack, cardFront;

    private boolean isFlipped = false;

    private final List<String> professions = Arrays.asList(Information.professions);

    private final List<String> phobias = Arrays.asList(Information.phobias);
    private final List<String> illnesses = Arrays.asList(Information.illnesses);
    private final List<String> baggage = Arrays.asList(Information.baggage);
    private final List<String> addInfos = Arrays.asList(Information.addInfo);

    private int height;
    private int counter = 0;

    private String username, profession, phobia, illness, baggageInfo, addInfo;

    private int age;

    private TextView professionText, ageText, phobiaText, illnessText, baggageText, addInfoText, cardName;


    private ArrayList<Teammate> teammatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);
        cardBack = findViewById(R.id.cardBack);
        cardBack.setBackground(getResources().getDrawable(R.drawable.card_background));
        cardFront = findViewById(R.id.cardFront);
        cardFront.setBackground(getResources().getDrawable(R.drawable.card_front_side));
        cardName = findViewById(R.id.card_name);
        professionText = findViewById(R.id.profession_value);
        ageText = findViewById(R.id.age_value);
        phobiaText = findViewById(R.id.phobia_value);
        illnessText = findViewById(R.id.illness_value);
        baggageText = findViewById(R.id.baggage_value);
        addInfoText = findViewById(R.id.add_info_value);
        RelativeLayout card = findViewById(R.id.mainCard);
        teammatesList = JsonUtil.readFromJson(this);
        cardName.setText(teammatesList.get(0).getName());
        randomizeCardInfo();
        card.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = (card.getHeight()) / 2;
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < teammatesList.size()) {
                    flipAnimation();
                } else {
                    Intent intent = new Intent(CardFlip.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void randomizeCardInfo() {
        Collections.shuffle(professions);
        Collections.shuffle(phobias);
        Collections.shuffle(illnesses);
        Collections.shuffle(baggage);
        Collections.shuffle(addInfos);
    }

    private void setCardInfo() {
        Random rand = new Random();
        age = rand.nextInt(Information.age[1] - Information.age[0] + 1) + Information.age[0];
        username = teammatesList.get(counter).getName();
//        email = teammatesList.get(counter).getEmail();
        profession = professions.get(counter);
        professionText.setText(profession);
        ageText.setText(String.valueOf(age));
        phobia = phobias.get(counter);
        phobiaText.setText(phobia);
        illness = illnesses.get(counter);
        illnessText.setText(illness);
        baggageInfo = baggage.get(counter);
        baggageText.setText(baggageInfo);
        addInfo = addInfos.get(counter);
        addInfoText.setText(addInfo);
    }

    private void flipAnimation() {
        cardBack.animate().setDuration(200).translationY(height).start();
        cardFront.animate().setDuration(200).translationY(-1 * height).withEndAction(new Runnable() {
            @Override
            public void run() {
                if (!isFlipped) {
                    cardBack.setTranslationZ(-50);
                    cardFront.setTranslationZ(0);
                    setCardInfo();
//                    sendEmail();
                    counter++;
                    if (counter < teammatesList.size()) {
                        cardName.setText(teammatesList.get(counter).getName());
                    }
                } else {
                    cardBack.setTranslationZ(0);
                    cardFront.setTranslationZ(-50);
                }
                cardBack.animate().setDuration(200).translationY(0).start();
                cardFront.animate().setDuration(200).translationY(0).start();
                isFlipped = !isFlipped;
            }
        }).start();
    }

//    private void sendEmail() {
//        Map<String, String> dynamicData = new HashMap<>();
//        dynamicData.put("email", email);
//        dynamicData.put("username", username);
//        dynamicData.put("profession", profession);
//        dynamicData.put("age", String.valueOf(age));
//        dynamicData.put("phobia", phobia);
//        dynamicData.put("baggage", baggageInfo);
//        dynamicData.put("illness", illness);
//        dynamicData.put("add_info", addInfo);
//        try {
//            SendGridEmailSender.sendEmailWithTemplate(email,  dynamicData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}