package com.example.comicword.controller;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModelProvider;

import com.example.comicword.R;
import com.example.comicword.data.model.User;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.UserRepository;
import com.example.comicword.ui.activity.LoginActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.activity_admin.MainAdminActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginController {

    private LoginActivity loginActivity;
    private ActivityResultLauncher<Intent> signInLauncher = null;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private UserRepository userRepository;
    public LoginController(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;

        signInLauncher = loginActivity.registerForActivityResult(new FirebaseAuthUIActivityResultContract(),
                new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                    @Override
                    public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                        onSignInResult(result);
                    }
                });
        this.userRepository = new UserRepository();

    }

    public void createSignInIntent() {
        // Choose authentication providers
//
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.image_logo)
                .setTosAndPrivacyPolicyUrls(
                        "https://example.com/terms.html",
                        "https://example.com/privacy.html")
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {

        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Đăng nhập thành công, cập nhật giao diện và hiển thị thông tin người dùng
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                String phoneNumber;
                String userId = user.getUid();
                String name = user.getDisplayName();
                String email = user.getEmail();

                User nUser = new User();

                if(name == null){
                    phoneNumber = user.getPhoneNumber();

                    nUser = new User(userId, phoneNumber, email);
                } else {
                    nUser = new User(userId, name, email);
                }


                userRepository.addUser(nUser, new BaseRepository.OnDataFetchedListener<User, Boolean>() {
                    @Override
                    public void onDataFetched(User nUser, Boolean value) {
                        if(value == true) {
                            if (nUser.getUserRole().equals("admin")) {
                                loginActivity.startActivity(new Intent(loginActivity, MainAdminActivity.class));
                                Log.i("LOGIN_TAG1", "" + nUser.getUserId() + " userRole : " + nUser.getUserRole());
                                loginActivity.finish();
                            }
                            else {
                                Intent intent = new Intent(loginActivity, MainActivity.class);
                                intent.putExtra("userId", nUser.getUserId());
                                Log.i("LOGIN_TAG2", "" + nUser.getUserId());
                                loginActivity.startActivity(intent);
                                loginActivity.finish();
                            }
                        } else {
                            Intent intent = new Intent(loginActivity, MainActivity.class);
                            intent.putExtra("userId", nUser.getUserId());
                            Log.i("LOGIN_TAG3", "" + nUser.getUserId());
                            loginActivity.startActivity(intent);
                            loginActivity.finish();
                        }
                    }
                });
            }
        } else {
            // Đăng nhập thất bại hoặc người dùng hủy đăng nhập

        }
    }

    public void checkUserIsLogin() {
        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

       if(mUser != null) {

           userRepository.checkRole(mUser.getUid(), new BaseRepository.OnDataFetchedListener<String, Boolean>() {
               @Override
               public void onDataFetched(String userRole, Boolean value) {
                    Log.i("LOGIN_TAG4", "" + mUser.getUid()  + " role : " + userRole);
                   if(userRole.equals("user")) {
                       Intent intent = new Intent(loginActivity, MainActivity.class);
                       intent.putExtra("userId", mUser.getUid());
                       loginActivity.startActivity(intent);
                       loginActivity.finish();
                   } else {
                       loginActivity.startActivity(new Intent(loginActivity, MainAdminActivity.class));
                       loginActivity.finish();
                   }
               }
           });
       }

    }
}


