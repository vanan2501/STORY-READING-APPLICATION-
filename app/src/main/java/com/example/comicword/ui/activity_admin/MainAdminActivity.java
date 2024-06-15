package com.example.comicword.ui.activity_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.comicword.R;
import com.example.comicword.ui.activity.LoginActivity;
import com.example.comicword.ui.fragment.CategoryFragment;
import com.example.comicword.ui.fragment.HomeFragment;
import com.example.comicword.ui.fragment_admin.CategoryAdminFragment;
import com.example.comicword.ui.fragment_admin.CommentAdminFragment;
import com.example.comicword.ui.fragment_admin.RatingAdminFragment;
import com.example.comicword.ui.fragment_admin.StoryAdminFragment;
import com.example.comicword.ui.fragment_admin.UserAdminFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainAdminActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;

    private NavigationView navigationView;
    private ShapeableImageView shapeableImageView;

    private FragmentManager fragmentManager;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        fragmentManager = getSupportFragmentManager();

        navigationView = (NavigationView) findViewById(R.id.navigationMenuAdmin);
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBarAdmin);

        setSupportActionBar(topAppBar);


        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigationView.setVisibility(View.VISIBLE);
            }
        });
        setFragment();
        handleCloseNavigation();
        setListenerItemNavigation();
    }

    public void setFragment(){



        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        UserAdminFragment frag = UserAdminFragment.newInstance(user.getUid());

        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("user_admin_fragment");
        fragmentTransaction.commit();
    }

    public void setCLoseNavigtion(){
        navigationView.setVisibility(View.GONE);
    }

    public void handleCloseNavigation(){

        shapeableImageView = (ShapeableImageView) findViewById(R.id.navigationCloseAdmin);


        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCLoseNavigtion();
            }
        });
    }

    public void setListenerItemNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//                if (fragmentStack.size() >= MAX_FRAGMENT_COUNT) {
//                    int fragmentsToRemove = fragmentStack.size() - MAX_FRAGMENT_COUNT + 1;
//                    for (int i = 0; i < fragmentsToRemove; i++) {
//                        fragmentManager.popBackStack();
//                        fragmentStack.remove(fragmentStack.size() - 1);
//                    }
//                }

                int itemId = item.getItemId();

                if (itemId == R.id.navUserAdmin) {
                    handleUserFragment();
                } else
                if (itemId == R.id.navCategoryAdmin) {
                    handldeCategoryFragment();
                } else
                if(itemId == R.id.navStoryAdmin){
                    handleStoryFragment();
                } else if ( itemId == R.id.navCommentAdmin){
                    handleCommentFragment();
                } else if(itemId == R.id.navRatingAdmin){
                    handleRatingFragment();
                } else if(itemId == R.id.navLogoutAdmin){
                    handleNavLogout();
                } else {
                    setCLoseNavigtion();
                }

                setCLoseNavigtion();

                return true;
            }
        });
    }

    private void handleRatingFragment() {
        RatingAdminFragment frag = new RatingAdminFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("ratting_admin_fragment");
        fragmentTransaction.commit();
    }

    private void handleCommentFragment() {

        CommentAdminFragment frag = new CommentAdminFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("comment_admin_fragment");
        fragmentTransaction.commit();
    }

    private void handleStoryFragment() {
        StoryAdminFragment frag = new StoryAdminFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("story_admin_fragment");
        fragmentTransaction.commit();
    }

    private void handldeCategoryFragment() {
        CategoryAdminFragment frag = new CategoryAdminFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("catergory_admin_fragment");
        fragmentTransaction.commit();
    }

    private void handleUserFragment() {
        UserAdminFragment frag = UserAdminFragment.newInstance(user.getUid());

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, frag);
        fragmentTransaction.addToBackStack("user_admin_fragment");
        fragmentTransaction.commit();
    }

    private void handleNavLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainAdminActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}