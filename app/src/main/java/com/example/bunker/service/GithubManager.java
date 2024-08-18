package com.example.bunker.service;

import android.util.Base64;
import com.example.bunker.model.GithubContent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubManager {
    private GithubService service;

    private static final String PAT = "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";
    private static final String REPO = "bunker-app";
    private static final String OWNER = "Arse-N";



    public GithubManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubService.class);
    }

    public String pushHtmlToGitHub(String htmlContent, String playerName) {
        String path = playerName + ".html";
        String message = "Add " + playerName + "'s data";
        String encodedHtml = Base64.encodeToString(htmlContent.getBytes(), Base64.NO_WRAP);
        GithubContent content = new GithubContent(message, encodedHtml);
        String url = "https://arse-n.github.io/bunker-app/" + path;
        Call<Void> call = service.updateFile(
                "token " + PAT, OWNER, REPO, path, content
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Successfully pushed " + playerName + "'s data to GitHub.");
                } else {
                    System.err.println("Error pushing " + playerName + "'s data: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return url;
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

