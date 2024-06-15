package com.example.comicword.data.model;

public class Comment {
    private String commentContent;
    private String userId;
    private String authorUserName;
    private String storyId;

    private int indexComment;

    public Comment(String content, String userId, String authorUserName, String storyId, int indexComment){
        this.setCommentContent(content);
        this.setUserId(userId);
        this.setAuthorUserName(authorUserName);
        this.setStoryId(storyId);
        this.setIndexComment(indexComment);
    }

    public Comment(){

    }



    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public int getIndexComment() {
        return indexComment;
    }

    public void setIndexComment(int indexComment) {
        this.indexComment = indexComment;
    }
}
