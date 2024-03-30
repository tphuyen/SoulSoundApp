package com.project.soulsoundapp.model;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private String commentId;
    private String songId;
    private String userId;
    private String content;
    private Date timestamp;

    public Comment(String songId, String userId, String content) {
        this.commentId = UUID.randomUUID().toString();
        this.songId = songId;
        this.userId = userId;
        this.content = content;
        this.timestamp = new Date();
    }

    public Comment(String commentId, String songId, String userId, String content) {
        this.commentId = commentId;
        this.songId = songId;
        this.userId = userId;
        this.content = content;
        this.timestamp = new Date();
    }
    public String getSongId() {
        return songId;
    }

    public String getContentId() {
        return commentId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
