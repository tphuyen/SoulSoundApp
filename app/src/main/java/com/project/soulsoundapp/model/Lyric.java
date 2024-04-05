package com.project.soulsoundapp.model;

public class Lyric {

    private String timestamp;
    private String text;

    public Lyric(String timestamp, String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }
}
