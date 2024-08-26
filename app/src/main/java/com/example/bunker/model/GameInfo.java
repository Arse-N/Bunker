package com.example.bunker.model;

public class GameInfo {

    private String gameId;
    private boolean genderIncluded;

    public GameInfo() {
        this.genderIncluded = false;
    }

    public boolean isGenderIncluded() {
        return genderIncluded;
    }

    public void setGenderIncluded(boolean genderIncluded) {
        this.genderIncluded = genderIncluded;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
