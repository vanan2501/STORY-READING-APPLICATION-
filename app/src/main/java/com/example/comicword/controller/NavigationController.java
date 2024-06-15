package com.example.comicword.controller;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicword.R;
import com.example.comicword.data.repository.FavoriteRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.LoginActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.fragment.CategoryFragment;
import com.example.comicword.ui.fragment.FavoriteFragment;
import com.example.comicword.ui.fragment.HistoryFragment;
import com.example.comicword.ui.fragment.HomeFragment;
import com.example.comicword.ui.fragment.RatingFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class NavigationController {

    private CoordinatorLayout coordinatorLayout;
    private MainActivity mainActivity;
    private NavigationView navigationView;

    private int itemIdPresent = R.id.navHome;
    private ShapeableImageView shapeableImageView;

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final int MAX_FRAGMENT_COUNT = 5; // Số lượng fragment tối đa trong stack
    private List<Integer> fragmentStack = new ArrayList<>(); // Danh sách theo dõi fragment

    public NavigationController(MainActivity mainActivity){
        this.navigationView = (NavigationView) mainActivity.findViewById(R.id.navigationMenu);
        this.mainActivity = mainActivity;
        this.handleCloseNavigation();
        this.setListenerItemNavigation();
        this.fragmentManager = mainActivity.getSupportFragmentManager();

    }

    public void closeNavigation(){
        navigationView.setVisibility(View.GONE);
    }

    public void handleCloseNavigation(){

        shapeableImageView = (ShapeableImageView) mainActivity.findViewById(R.id.navigationClose);


        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeNavigation();
            }
        });
    }

    public void setListenerItemNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (fragmentStack.size() >= MAX_FRAGMENT_COUNT) {
                    int fragmentsToRemove = fragmentStack.size() - MAX_FRAGMENT_COUNT + 1;
                    for (int i = 0; i < fragmentsToRemove; i++) {
                        fragmentManager.popBackStack();
                        fragmentStack.remove(fragmentStack.size() - 1);
                    }
                }

                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {

                   handleNavHome();
                } else
                    if (itemId == R.id.navFavorite) {

                    handleNavFavorite();
                } else
                    if(itemId == R.id.navHistory){

                    handleNavHistory();
                } else
                    if(itemId == R.id.navCategory) {

                    handleNavCategory();
                } else
                    if ( itemId == R.id.navRating){

                    handleNavRating();
                } else if(itemId == R.id.navLogout){
                    handleNavLogout();
                } else {
                        closeNavigation();
                    }

                closeNavigation();

                return true;
            }
        });
    }

    private void handleNavHome() {
        if(itemIdPresent == R.id.navHome ){
            closeNavigation();
        } else {
            HomeFragment frag = new HomeFragment();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, frag);
            fragmentTransaction.addToBackStack("home_fragment");
            fragmentTransaction.commit();
        }
        itemIdPresent = R.id.navHome;
    }

    private void handleNavFavorite() {
        if(itemIdPresent == R.id.navFavorite ){
            closeNavigation();
        } else {
            FavoriteFragment frag = FavoriteFragment.newInstance(user.getUid());

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, frag);
            fragmentTransaction.addToBackStack("favorite_fragment");
            fragmentTransaction.commit();
        }
        itemIdPresent = R.id.navFavorite;
    }

    private void handleNavHistory() {
        if(itemIdPresent == R.id.navHistory ){
            closeNavigation();
        } else {
            HistoryFragment frag = HistoryFragment.newInstance(user.getUid());

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, frag);
            fragmentTransaction.addToBackStack("history_fragment");
            fragmentTransaction.commit();
        }
        itemIdPresent = R.id.navHistory;
    }

    private void handleNavCategory() {
        if(itemIdPresent == R.id.navCategory ){
            closeNavigation();
        } else {
            CategoryFragment frag = new CategoryFragment();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, frag);
            fragmentTransaction.addToBackStack("category_fragment");
            fragmentTransaction.commit();
        }
        itemIdPresent = R.id.navCategory;
    }

    private void handleNavRating() {
        if(itemIdPresent == R.id.navRating ){
            closeNavigation();
        } else {
            RatingFragment frag = new RatingFragment();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, frag);
            fragmentTransaction.addToBackStack("rating_fragment");
            fragmentTransaction.commit();
        }
        itemIdPresent = R.id.navRating;
    }

    private void handleNavLogout() {
        AuthUI.getInstance()
                .signOut(mainActivity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mainActivity.startActivity(new Intent(mainActivity, LoginActivity.class));
                        mainActivity.finish();
                    }
                });
    }
}
