package com.example.bunker.util;
import android.content.Context;
import com.example.bunker.model.GameInfo;
import com.example.bunker.model.Teammate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonUtil {
    private static final String PLAYERS_FILE_NAME = "players.json";
    private static final String GAME_INFO_FILE_NAME = "game_info.json";

    public static void writeToPlayersJson(Context context, ArrayList<Teammate> teammatesList) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(teammatesList);
        File file = new File(context.getExternalFilesDir(null), PLAYERS_FILE_NAME);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Teammate> readFromPlayersJson(Context context) {
        File file = new File(context.getExternalFilesDir(null), PLAYERS_FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Teammate>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeToGameInfoJson(Context context, GameInfo gameInfo) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(gameInfo);
        File file = new File(context.getExternalFilesDir(null), GAME_INFO_FILE_NAME);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameInfo readFromGameInfoJson(Context context) {
        File file = new File(context.getExternalFilesDir(null), GAME_INFO_FILE_NAME);
        if (!file.exists()) {
            return null;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<GameInfo>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
