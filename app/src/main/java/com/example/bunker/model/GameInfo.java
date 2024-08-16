package com.example.bunker.model;

public class GameInfo {

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
}
