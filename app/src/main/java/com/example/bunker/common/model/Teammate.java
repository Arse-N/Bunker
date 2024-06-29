package com.example.bunker.common.model;

public class Teammate {
    private String name;
    private String email;

    public Teammate(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}