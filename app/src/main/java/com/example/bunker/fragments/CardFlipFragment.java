package com.example.bunker.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.bunker.MainActivity;
import com.example.bunker.R;
import com.example.bunker.constants.Information;
import com.example.bunker.model.GameInfo;
import com.example.bunker.model.Teammate;
import com.example.bunker.service.GithubManager;
import com.example.bunker.service.HtmlFileGenerator;
import com.example.bunker.util.JsonUtil;
import com.example.bunker.util.QRCodeUtils;

import java.util.*;

public class CardFlipFragment extends Fragment {

    private CardView cardBack, cardFront;
    private boolean isFlipped = false;

    private final List<String> professions = Arrays.asList(Information.professions);
    private final List<String> phobias = Arrays.asList(Information.phobias);
    private final List<String> illnesses = Arrays.asList(Information.illnesses);
    private final List<String> baggage = Arrays.asList(Information.baggage);
    private final List<String> addInfos = Arrays.asList(Information.addInfo);

    private final List<String> gender = Arrays.asList(Information.gender);

    private int height;
    private int counter = 0;

    private String username, profession, phobia, illness, baggageInfo, addInfo;
    private int age;

    private TextView professionText, ageText, phobiaText, illnessText, baggageText, addInfoText, cardName;
    private ArrayList<Teammate> teammatesList;

    private List<String> urls;

    private GameInfo gameInfo;

    private GithubManager githubManager;

    private HtmlFileGenerator htmlFileGenerator;

    private ArrayList<String> chosenGender;
    private Random random;

    private int menCount = 0;
    private int womenCount = 0;
    private ImageView qrCodeImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_flip, container, false);

        cardBack = view.findViewById(R.id.cardBack);
        cardBack.setBackground(getResources().getDrawable(R.drawable.card_background));
        cardFront = view.findViewById(R.id.cardFront);
        cardFront.setBackground(getResources().getDrawable(R.drawable.card_front_side));
        cardName = view.findViewById(R.id.card_name);
        professionText = view.findViewById(R.id.profession_value);
        ageText = view.findViewById(R.id.age_value);
        phobiaText = view.findViewById(R.id.phobia_value);
        illnessText = view.findViewById(R.id.illness_value);
        baggageText = view.findViewById(R.id.baggage_value);
        addInfoText = view.findViewById(R.id.add_info_value);
        RelativeLayout card = view.findViewById(R.id.mainCard);
        chosenGender = new ArrayList<>();
        random = new Random();
        githubManager = new GithubManager();
        htmlFileGenerator = new HtmlFileGenerator();
        teammatesList = JsonUtil.readFromPlayersJson(requireContext());
        gameInfo = JsonUtil.readFromGameInfoJson(requireContext());
        cardName.setText(teammatesList.get(0).getName());
        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
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
                    menCount = 0;
                    womenCount = 0;
                    chosenGender = new ArrayList<>();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    private void randomizeCardInfo() {
        Collections.shuffle(professions);
        Collections.shuffle(phobias);
        Collections.shuffle(illnesses);
        Collections.shuffle(baggage);
        Collections.shuffle(addInfos);
    }

    private void setCardInfo(boolean isGenderIncluded) {
        age = random.nextInt(Information.age[1] - Information.age[0] + 1) + Information.age[0];
        StringBuilder bio = new StringBuilder();
        if (isGenderIncluded) {
            bio.append(getGender()).append(", ").append(age);
        } else {
            bio.append(age);
        }
        try {
            String url = generateFile(teammatesList.get(counter));
            Bitmap qrCodeBitmap = QRCodeUtils.generateQRCodeByUrl(url);
            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        username = teammatesList.get(counter).getName();
        profession = professions.get(counter);
        professionText.setText(profession);
        ageText.setText(bio);
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
                    setCardInfo(gameInfo.isGenderIncluded());
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

    private String getGender() {
        String man = gender.get(1);
        String woman = gender.get(0);
        String selected = gender.get(random.nextInt(gender.size()));
        if (chosenGender.size() < 4) {
            if (menCount < 2 && womenCount < 2) {
                chosenGender.add(selected);
                if (selected.equals(man)) {
                    menCount++;
                } else if (selected.equals(woman)) {
                    womenCount++;
                }
                return selected;
            } else if (menCount < 2) {
                chosenGender.add(man);
                menCount++;
                return man;
            } else {
                chosenGender.add(woman);
                womenCount++;
                return woman;
            }
        } else {
            chosenGender.add(selected);
            return selected;
        }
    }


    public String generateFile(Teammate teammate) {
        String htmlContent = htmlFileGenerator.createHtmlContentForPlayer(teammate.getName(), teammate.getName());
        return githubManager.pushHtmlToGitHub(htmlContent, teammate.getName());
    }
}
