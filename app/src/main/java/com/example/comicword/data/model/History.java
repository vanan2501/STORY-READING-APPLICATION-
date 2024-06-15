package com.example.comicword.data.model;

import java.sql.Date;

public class    History {
    private String userId;
    private String storyId;
    private String historyTimeTamp;

    public History(String userId, String story_id, String timeTamp){
        this.setStoryId(story_id);
        this.setHistoryTimeTamp(timeTamp);
        this.setUserId(userId);
    }

    public History(){

    }


    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getHistoryTimeTamp() {
        return historyTimeTamp;
    }

    public void setHistoryTimeTamp(String historyTimeTamp) {
        this.historyTimeTamp = historyTimeTamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
