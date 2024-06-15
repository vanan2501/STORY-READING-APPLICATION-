package com.example.comicword.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.comicword.R;
import com.example.comicword.controller.MenuController;
import com.example.comicword.controller.NavigationController;
import com.example.comicword.ui.fragment.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private MenuController menuController;
    private MaterialToolbar topAppBar;

    public FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuController = new MenuController(this);


        // handling navbar events
        NavigationController navigationController = new NavigationController(this);

        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);

        setSupportActionBar(topAppBar);

        fragmentManager = getSupportFragmentManager();

        this.setFragment();

    }

    public void setFragment(){



        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment frag = new HomeFragment();

        fragmentTransaction.replace(R.id.fragmentContainer, frag);
        fragmentTransaction.addToBackStack("home_fragment");
        fragmentTransaction.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_left_app_bar, menu);

        return menuController.onCreateOptionsMenu(menu, topAppBar);
    }

}