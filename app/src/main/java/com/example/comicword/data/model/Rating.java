package com.example.comicword.data.model;

public class Rating {

    private String userId;
    private int value;
    private String storyId;

    private String ratingTimeTamp;

    public Rating(String userId, int value, String storyId, String ratingTimeTamp){
        this.setUserId(userId);
        this.setValue(value);
        this.setStoryId(storyId);
        this.setRatingTimeTamp(ratingTimeTamp);
    }


    public Rating(){

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getRatingTimeTamp() {
        return ratingTimeTamp;
    }

    public void setRatingTimeTamp(String ratingTimeTamp) {
        this.ratingTimeTamp = ratingTimeTamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
