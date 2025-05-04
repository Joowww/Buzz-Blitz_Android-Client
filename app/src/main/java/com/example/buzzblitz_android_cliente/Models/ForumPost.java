package com.example.buzzblitz_android_cliente.Models;

public class ForumPost {
    private String authorId;
    private String content;
    private String title;
    private int likes;
    private String createdAt;

    public ForumPost(String authorId, String content, String title, int likes, String createdAt) {
        this.authorId = authorId;
        this.content = content;
        this.title = title;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
