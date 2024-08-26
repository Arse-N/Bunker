package com.example.bunker.service;

import android.content.Context;
import android.util.Base64;
import com.example.bunker.dto.GithubFile;
import com.example.bunker.model.GameInfo;
import com.example.bunker.model.GithubContent;
import com.example.bunker.util.BaseUtil;
import com.example.bunker.util.JsonUtil;
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

    private final Context context;

    public GithubManager(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubService.class);
        this.context = context;
    }

    public String pushJSONToGitHub(JSONObject playersData) {
        String JSONFileName = BaseUtil.generateCode();
        GameInfo gameInfo = JsonUtil.readFromGameInfoJson(context);
        if(gameInfo!=null){
            gameInfo.setGameId(JSONFileName);
            JsonUtil.writeToGameInfoJson(context, gameInfo);
        }
        String path = JSONFileName + ".json";
        String message = "Add " + JSONFileName + "'s data";

        try {
            String jsonString = playersData.toString();
            String encodedJson = Base64.encodeToString(jsonString.getBytes(), Base64.NO_WRAP);

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


    public void deleteJsonFromGitHub() {
        GameInfo gameInfo = JsonUtil.readFromGameInfoJson(context);
        if (gameInfo != null && gameInfo.getGameId() != null) {
            String fileName = gameInfo.getGameId();
            String path = fileName + ".json";
            String message = "Delete " + fileName + "'s data";

            Call<GithubFile> getFileInfoCall = service.getFileInfo(
                    "token " + PAT, OWNER, REPO, path
            );

            getFileInfoCall.enqueue(new Callback<GithubFile>() {
                @Override
                public void onResponse(Call<GithubFile> call, Response<GithubFile> response) {
                    if (response.isSuccessful()) {
                        GithubFile file = response.body();
                        if (file != null) {
                            String sha = file.getSha();

                            // Log SHA and other details
                            System.out.println("Deleting file with SHA: " + sha);

                            // Delete the file
                            Call<Void> deleteCall = service.deleteFile(
                                    "token " + PAT, OWNER, REPO, path, message, sha
                            );

                            deleteCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        System.out.println("Successfully deleted " + fileName + "'s data from GitHub.");
                                    } else {
                                        System.err.println("Error deleting " + fileName + "'s data: " + response.code() + " " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } else {
                            System.err.println("File not found or invalid response");
                        }
                    } else {
                        System.err.println("Error retrieving file info: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<GithubFile> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


}

