package com.example.bunker.service;

import android.util.Base64;
import com.example.bunker.model.GithubContent;
import com.example.bunker.util.BaseUtil;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubManager {
    private GithubService service;

    private static final String PAT = "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";
    private static final String REPO = "bunker-games";
    private static final String OWNER = "bunker-gameees";



    public GithubManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubService.class);
    }

    public String pushHtmlToGitHub(String htmlContent) {
        String htmlName = BaseUtil.generateCode();
        String path = htmlName + ".html";
        String message = "Add " + htmlName + "'s data";
        String encodedHtml = Base64.encodeToString(htmlContent.getBytes(), Base64.NO_WRAP);
        GithubContent content = new GithubContent(message, encodedHtml);
        String url = "https://bunker-gameees.github.io/bunker-games/" + path;
        Call<Void> call = service.updateFile(
                "token " + PAT, OWNER, REPO, path, content
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Successfully pushed " + htmlName + "'s data to GitHub.");
                } else {
                    System.err.println("Error pushing " + htmlName + "'s data: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return url;
    }

    public String pushJSONToGitHub(JSONObject playersData) {
        String JSONFileName = BaseUtil.generateCode();
        String path = JSONFileName + ".json";
        String message = "Add " + JSONFileName + "'s data";

        try {
            String jsonString = playersData.toString();
            String encodedJson = Base64.encodeToString(jsonString.getBytes(), Base64.NO_WRAP); // Encode to Base64

            GithubContent content = new GithubContent(message, encodedJson);

            Call<Void> call = service.updateFile(
                    "token " + PAT, OWNER, REPO, path, content
            );

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Successfully pushed " + JSONFileName + "'s data to GitHub.");
                    } else {
                        System.err.println("Error pushing " + JSONFileName + "'s data: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            String url = "https://bunker-gameees.github.io/bunker-games/card.html?gid=" + JSONFileName + "&pid=";
            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deleteHtmlFromGitHub(String playerName) {
        String path = playerName + ".html";
        String message = "Delete " + playerName + "'s data";
        GithubContent content = new GithubContent(message, null);

        Call<Void> call = service.deleteFile(
                "token " + PAT, OWNER, REPO, path, content
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Successfully deleted " + playerName + "'s data from GitHub.");
                } else {
                    System.err.println("Error deleting " + playerName + "'s data: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

