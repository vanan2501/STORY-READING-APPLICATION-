package com.example.comicword.data.model;

public class Favorite {

    private String storyId;
    private String userId;

    public Favorite(String storyId, String userId){
        this.setStoryId(storyId);
        this.setUserId(userId);
    }

    public Favorite(){}

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
