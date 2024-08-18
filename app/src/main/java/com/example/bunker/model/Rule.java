package com.example.bunker.model;

public class Rule {
    private String header;
    private String description;

    public Rule(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }
}