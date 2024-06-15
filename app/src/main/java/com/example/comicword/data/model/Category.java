package com.example.comicword.data.model;

public class Category {
    private String categoryTitle;

    private boolean isDelete;

    public Category(){}

    public Category(String title, boolean is_delete){
        this.setCategoryTitle(title);
        this.setDelete(is_delete);
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }


    public boolean getIsDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
