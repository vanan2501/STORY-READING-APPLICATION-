package com.example.comicword.data.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.UserInfo;

import java.util.List;

public class User extends ViewModel {
    private String userId;
    private String userName;
    private String userEmail;
    private String userRole;

    public User(String userId, String name, String email) {
        this.setUserId(userId);
        this.setUserName(name);
        this.setUserEmail(email);
        this.setUserRole("user");
    }

    public User(){

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
