package com.example.bunker.dto;

import android.graphics.Bitmap;

public class CardInfo {

    private String id;
    private String username;
    private String profession;
    private String bio;
    private String phobia;
    private String illness;
    private String baggage;
    private String additionalInfo;

    private Bitmap qrCodeBitmap;

    public CardInfo(String username, String profession, String bio, String phobia, String illness, String baggage, String additionalInfo) {
        this.username = username;
        this.profession = profession;
        this.bio = bio;
        this.phobia = phobia;
        this.illness = illness;
        this.baggage = baggage;
        this.additionalInfo = additionalInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public String getBio() {
        return bio;
    }

    public String getPhobia() {
        return phobia;
    }

    public String getIllness() {
        return illness;
    }

    public String getBaggage() {
        return baggage;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getQrCodeBitmap() {
        return qrCodeBitmap;
    }

    public void setQrCodeBitmap(Bitmap qrCodeBitmap) {
        this.qrCodeBitmap = qrCodeBitmap;
    }
}
