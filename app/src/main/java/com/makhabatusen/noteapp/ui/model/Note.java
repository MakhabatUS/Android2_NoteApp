package com.makhabatusen.noteapp.ui.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private long createdAt;

    public Note() {
    }

    public Note(String title, long createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
