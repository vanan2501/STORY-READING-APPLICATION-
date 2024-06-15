package com.example.comicword.data.model;

import java.util.List;
import java.util.Map;

public class Chapter {
    private String storyId;
    private Map<String, Object> chapterContent;

    public Chapter(String storyId, Map<String, Object> chapterContent){

        this.setChapterContent(chapterContent);
        this.setStoryId(storyId);
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }


    public Map<String, Object> getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(Map<String, Object> chapterContent) {
        this.chapterContent = chapterContent;
    }
}
