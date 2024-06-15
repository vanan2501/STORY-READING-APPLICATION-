package com.example.comicword.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicword.R;
//import com.example.comicword.controller.DetailStoryController;
//import com.example.comicword.data.model.Chapter;
//import com.example.comicword.data.repository.BaseRepository;
//import com.example.comicword.data.repository.ChapterRepository;
//import com.example.comicword.ui.adapter.ChapterAdapter;
import com.example.comicword.controller.DetailStoryController;
import com.example.comicword.data.model.Chapter;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.ChapterRepository;
import com.example.comicword.ui.adapter.ChapterAdapter;
import com.example.comicword.ui.adapter.ListStoryAdapter;
import com.example.comicword.ui.fragment.DetailStoryFragment;
import com.example.comicword.ui.fragment.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailStoryActivity extends AppCompatActivity {
    private String storyId;
    private String storyType;
    private String storyTitle;
    public DetailStoryController detailStoryController;
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if(intent != null){
            storyId = intent.getStringExtra("storyId");
            storyType = intent.getStringExtra("storyType");
            storyTitle = intent.getStringExtra("storyTitle");

            if(storyId != null)
            {
                detailStoryController = new DetailStoryController(this, storyId, storyTitle);
                detailStoryController.showFragment(storyId, storyType);
            } else {
                Toast.makeText(this, "khong co Story id", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Loi khi tai du lieu", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_app_bar, menu);

        return detailStoryController.onCreateOptionsMenu(menu);
    }
}