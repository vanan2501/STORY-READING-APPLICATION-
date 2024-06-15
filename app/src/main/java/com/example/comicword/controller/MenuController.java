package com.example.comicword.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.comicword.R;
import com.example.comicword.ui.activity.LoginActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.fragment.CategoryFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuController {

    private NavigationView navigationView;

    private MainActivity mainActivity;

    public MenuController(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

    }


    public boolean onCreateOptionsMenu(Menu menu, MaterialToolbar topAppBar) {



        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        MenuItem searchItem = menu.findItem(R.id.search);

        searchItem.setVisible(false).setShowAsAction(0);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.equals(favoriteItem)){
                    Toast.makeText(mainActivity, "Favorite item", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return true;
            }
        });

        setToolBar(topAppBar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFragmentVisible("category_story_fragment")) {
                    FragmentTransaction fragmentTransaction = mainActivity.fragmentManager.beginTransaction();

                    CategoryFragment frag = new CategoryFragment();

                    frag.setHasOptionsMenu(false);
                    fragmentTransaction.replace(R.id.fragmentContainer, frag,"category_fragment");
                    fragmentTransaction.addToBackStack("category_fragment");
                    fragmentTransaction.commit();
                } else {
                    navigationView = (NavigationView) mainActivity.findViewById(R.id.navigationMenu);

                    navigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        return true;
    }

    public void setToolBar(MaterialToolbar topAppBar)
    {
        if(isFragmentVisible("category_story_fragment")) {
            topAppBar.setTitle("");
            topAppBar.setNavigationIcon(R.drawable.arrow_back);
        } else {
            topAppBar.setTitle("Trang chủ");
            topAppBar.setNavigationIcon(R.drawable.top_app_bar_menu);
        }

    }

    public boolean isFragmentVisible(String transactionName) {

        FragmentManager.BackStackEntry backStackEntry = mainActivity.fragmentManager.getBackStackEntryAt(mainActivity.fragmentManager.getBackStackEntryCount() - 1);

        return backStackEntry != null && backStackEntry.getName().equals(transactionName);
    }
}
