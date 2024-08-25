package com.example.bunker.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.bunker.MainActivity;
import com.example.bunker.R;
import com.example.bunker.constants.Information;
import com.example.bunker.dto.CardInfo;
import com.example.bunker.model.GameInfo;
import com.example.bunker.model.Teammate;
import com.example.bunker.service.GithubManager;
import com.example.bunker.service.HtmlFileGenerator;
import com.example.bunker.util.BaseUtil;
import com.example.bunker.util.JsonUtil;
import com.example.bunker.util.QRCodeUtils;
import org.json.JSONObject;

import java.util.*;

public class CardFlipFragment extends Fragment {

    private ConstraintLayout cardBack, cardFront;
    private boolean isFrontVisible = false;

    private final List<String> professions = Arrays.asList(Information.professions);
    private final List<String> phobias = Arrays.asList(Information.phobias);
    private final List<String> illnesses = Arrays.asList(Information.illnesses);
    private final List<String> baggage = Arrays.asList(Information.baggage);
    private final List<String> addInfos = Arrays.asList(Information.addInfo);

    private final List<String> gender = Arrays.asList(Information.gender);

    private int counter = 0;

    private TextView professionText, ageText, phobiaText, illnessText, baggageText, addInfoText, cardName;
    private ArrayList<Teammate> teammatesList;

    private GameInfo gameInfo;

    private GithubManager githubManager;

    private HtmlFileGenerator htmlFileGenerator;

    private ArrayList<String> chosenGender;
    private Random random;

    private int menCount = 0;
    private int womenCount = 0;
    private ArrayList<CardInfo> playersCardInfos;
    private ImageView qrCodeImageView;
    private Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_flip, container, false);
        cardBack = view.findViewById(R.id.cardBack);
        cardFront = view.findViewById(R.id.cardFront);
        cardFront.setVisibility(View.GONE);
        cardName = view.findViewById(R.id.card_name);
        professionText = view.findViewById(R.id.profession_value);
        ageText = view.findViewById(R.id.age_value);
        phobiaText = view.findViewById(R.id.phobia_value);
        illnessText = view.findViewById(R.id.illness_value);
        baggageText = view.findViewById(R.id.baggage_value);
        addInfoText = view.findViewById(R.id.add_info_value);
        next = view.findViewById(R.id.next_button);
        ConstraintLayout card = view.findViewById(R.id.mainCard);
        chosenGender = new ArrayList<>();
        random = new Random();
        githubManager = new GithubManager();
        htmlFileGenerator = new HtmlFileGenerator();
        teammatesList = JsonUtil.readFromPlayersJson(requireContext());
        gameInfo = JsonUtil.readFromGameInfoJson(requireContext());
        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        randomizeCardInfo();
        generateData(gameInfo.isGenderIncluded());
        setCardInfo();
        setCameraDistance();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < teammatesList.size()) {
                    cardName.setText(teammatesList.get(counter).getName());
                    setCardInfo();
                    next.setVisibility(View.GONE);
                } else {
                    menCount = 0;
                    womenCount = 0;
                    chosenGender = new ArrayList<>();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                if(counter == teammatesList.size()-1){
                    next.setText(getString(R.string.start));
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

    private void generateData(boolean isGenderIncluded) {
        JSONObject playersData = new JSONObject();
        playersCardInfos = new ArrayList<>();
        for (int i = 0; i < teammatesList.size(); i++) {
            CardInfo cardInfo = getCardInfo(isGenderIncluded, i);
            cardInfo.setId(BaseUtil.generateCode());
            JsonUtil.createJson(cardInfo, playersData);
            playersCardInfos.add(cardInfo);
        }
        String url = generateFile(playersData);
        for(CardInfo playerInfo: playersCardInfos){
            try {
                Bitmap qrCodeBitmap = QRCodeUtils.generateQRCodeByUrl(url + playerInfo.getId());
                playerInfo.setQrCodeBitmap(qrCodeBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private CardInfo getCardInfo(boolean isGenderIncluded, int id) {
        StringBuilder bio = new StringBuilder();
        int age = random.nextInt(Information.age[1] - Information.age[0] + 1) + Information.age[0];
        String username = teammatesList.get(id).getName();
        String profession = professions.get(id);
        String phobia = phobias.get(id);
        String illness = illnesses.get(id);
        String baggageInfo = baggage.get(id);
        String addInfo = addInfos.get(id);
        if (isGenderIncluded) {
            bio.append(getGender()).append(", ").append(age);
        } else {
            bio.append(age);
        }
        CardInfo cardInfo = new CardInfo(username, profession, bio.toString(), phobia, illness, baggageInfo, addInfo);
        return cardInfo;
    }

    private void setCardInfo() {
        CardInfo cardInfo = playersCardInfos.get(counter);
        cardName.setText(cardInfo.getUsername().isEmpty() ? getString(R.string.teammate) + (counter + 1) : cardInfo.getUsername());
        professionText.setText(cardInfo.getProfession());
        ageText.setText(cardInfo.getBio());
        phobiaText.setText(cardInfo.getPhobia());
        illnessText.setText(cardInfo.getIllness());
        baggageText.setText(cardInfo.getBaggage());
        addInfoText.setText(cardInfo.getAdditionalInfo());
        qrCodeImageView.setImageBitmap(cardInfo.getQrCodeBitmap());
        counter++;
    }

    private void setCameraDistance() {
        float scale = getResources().getDisplayMetrics().density;
        cardFront.setCameraDistance(8000 * scale);
        cardBack.setCameraDistance(8000 * scale);
    }

    private void flipCard() {
        if (isFrontVisible) {
            cardFront.animate()
                    .rotationY(90)
                    .setDuration(200)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            cardFront.setVisibility(View.GONE);
                            cardBack.setRotationY(-90);
                            cardBack.setVisibility(View.VISIBLE);
                            cardBack.animate()
                                    .rotationY(0)
                                    .setDuration(200)
                                    .start();
                            next.setVisibility(View.VISIBLE);
                        }
                    }).start();
        } else {
            cardBack.animate()
                    .rotationY(-90)
                    .setDuration(200)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            cardBack.setVisibility(View.GONE);
                            cardFront.setRotationY(90);
                            cardFront.setVisibility(View.VISIBLE);
                            cardFront.animate()
                                    .rotationY(0)
                                    .setDuration(200)
                                    .start();
                            next.setVisibility(View.GONE);
                        }
                    }).start();
        }
        isFrontVisible = !isFrontVisible;
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


    public String generateFile(JSONObject playersDataJson) {
        return githubManager.pushJSONToGitHub(playersDataJson);
    }

}
