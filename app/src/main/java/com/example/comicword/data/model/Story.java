package com.example.comicword.data.model;

public class Story {
    private String storyTitle;
    private String storyAuthor;
    private String storyDescription;
    private String sotryCoverImageUrl;

    private String storyType;
    private String categoryId;

    private boolean isDelete;


    public Story(String title, String author, String description, String coverImageUrl, String categoryId) {
        this.setStoryTitle(title);
        this.setStoryAuthor(author);
        this.setStoryDescription(description);
        this.setSotryCoverImageUrl(coverImageUrl);
        this.setCategoryId(categoryId);
        this.setDelete(false);
    }

    public Story(){

    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryAuthor() {
        return storyAuthor;
    }

    public void setStoryAuthor(String storyAuthor) {
        this.storyAuthor = storyAuthor;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    public String getSotryCoverImageUrl() {
        return sotryCoverImageUrl;
    }

    public void setSotryCoverImageUrl(String sotryCoverImageUrl) {
        this.sotryCoverImageUrl = sotryCoverImageUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStoryType() {
        return storyType;
    }

    public void setStoryType(String storyType) {
        this.storyType = storyType;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
