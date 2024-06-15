package com.example.comicword.data.model;

import java.util.List;
import java.util.Map;

public class Bookmark {

    private String userId;
    private String storyId;
    private List<String> chapterNumbers;

    public Bookmark(String userId, String storyId, List<String> chapterNumbers){
        this.setUserId(userId);
        this.setStoryId(storyId);
        this.setChapterNumbers(chapterNumbers);
    }

    public Bookmark(){}

    public List<String> getChapterNumbers() {
        return chapterNumbers;
    }

    public void setChapterNumbers(List<String> chapterNumbers) {
        this.chapterNumbers = chapterNumbers;
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
}
