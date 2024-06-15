package com.example.comicword.ui.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comicword.R;
import com.example.comicword.controller.LoginController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnLogin;

    private LoginController loginController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (MaterialButton) findViewById(R.id.btnLogin);

        loginController = new LoginController(this);

        loginController.checkUserIsLogin();
        // handling action submit button login
        login(btnLogin);
    }

    private void login(@NonNull MaterialButton btnLogin) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.createSignInIntent();
            }
        });
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        return super.onNavigateUpFromChild(child);

    }
}