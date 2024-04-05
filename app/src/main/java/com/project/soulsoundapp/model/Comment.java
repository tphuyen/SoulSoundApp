package com.project.soulsoundapp.model;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private String songId;
    private String email;
    private String content;

    public Comment(String songId, String email, String content) {
        this.songId = songId;
        this.email = email;
        this.content = content;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
