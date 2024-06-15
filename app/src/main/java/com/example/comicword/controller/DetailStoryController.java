package com.example.comicword.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.comicword.R;
import com.example.comicword.data.model.Bookmark;
import com.example.comicword.data.model.Favorite;
import com.example.comicword.data.repository.BookmarkReposiitory;
import com.example.comicword.data.repository.ChapterRepository;
import com.example.comicword.data.repository.FavoriteRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.fragment.CommentStoryFragment;
import com.example.comicword.ui.fragment.DetailStoryFragment;
import com.example.comicword.ui.fragment.RatingStoryFragment;
import com.example.comicword.ui.fragment.ReadStoryFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DetailStoryController {

    private final static String TAG = "DETAIL_STORY_CONTROLLER";

    private DetailStoryFragment fragDetailStory;
    private DetailStoryActivity detailStoryActivity;

    public MaterialToolbar topAppBar;
    private String storyId;
    private String storyTitle;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public DetailStoryController(DetailStoryActivity detailStoryActivity, String storyId, String storyTitle){
        this.detailStoryActivity = detailStoryActivity;
        this.topAppBar = (MaterialToolbar) detailStoryActivity.findViewById(R.id.topAppBarDetailStory);
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.detailStoryActivity.setSupportActionBar(topAppBar);

    }


    public DetailStoryController(){}


    public void handleBackToMain(){
        detailStoryActivity.startActivity(new Intent(detailStoryActivity, MainActivity.class));
        detailStoryActivity.finish();
    }

    public void handleBackToDetailStory(){

    }

    public void showFragment(String storyId, String storyType){

        FragmentTransaction fragmentTransaction = detailStoryActivity.fragmentManager.beginTransaction();

        fragDetailStory = DetailStoryFragment.newInstance(storyId, storyType);

        fragmentTransaction.replace(R.id.fragmentContainer, fragDetailStory, "detail_story_fragment");
        fragmentTransaction.addToBackStack("detail_story_fragment");
        fragmentTransaction.commit();
    }

    // set title app bar
    public void setTitleTopAppBar() {
        topAppBar.setTitle("Thông tin");
    }

    public void setChangeToolBar(String chapterId){
        topAppBar.setTitle(chapterId);
    }

    public void setTopAppBar(){

        if ( isFragmentVisible("detail_story_fragment")) {
            // back to read story
            handleBackToMain();
        } else {
            // back to detail story
//            setTitleTopAppBar();

            FragmentTransaction fragmentTransaction = detailStoryActivity.fragmentManager.beginTransaction();

            // search fragment deatil story;
            fragmentTransaction.replace(R.id.fragmentContainer, fragDetailStory,"detail_story_fragment" );
            fragmentTransaction.addToBackStack("detail_story_fragment");
            fragmentTransaction.commit();
        }
    }


    public boolean isFragmentVisible(String transactionName) {

        FragmentManager.BackStackEntry backStackEntry = detailStoryActivity.fragmentManager.getBackStackEntryAt(detailStoryActivity.fragmentManager.getBackStackEntryCount() - 1);

        return backStackEntry != null && backStackEntry.getName().equals(transactionName);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        MenuItem ratingItem = menu.findItem(R.id.rating);
        MenuItem commentItem = menu.findItem(R.id.comment);
        MenuItem bookmarkItem = menu.findItem(R.id.bookmark);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.equals(favoriteItem)){

                    handleFavoriteStory();
                } else if(item.equals(ratingItem)) {

                    handleRatingStory();
                } else if(item.equals(commentItem)){
                    handleCommentStory();
                } else {
                    handleBookmark();
                }
                return false;
            }
        });

        if(isFragmentVisible("detail_story_fragment")){
            setTitleTopAppBar();
        }

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTopAppBar();
            }
        });
        return true;
    }

    public void handleBookmark() {
        if(isFragmentVisible("read_story_fragment")){

            int fragmentCount = detailStoryActivity.fragmentManager.getBackStackEntryCount();

            if (fragmentCount > 0) {
                FragmentManager.BackStackEntry backStackEntry = detailStoryActivity.fragmentManager.getBackStackEntryAt(fragmentCount - 1);

                if (backStackEntry.getName().equals("read_story_fragment")) {
                    Fragment fragment = detailStoryActivity.fragmentManager.findFragmentByTag(backStackEntry.getName());

                    if (fragment instanceof ReadStoryFragment) {
                        ReadStoryFragment readStoryFragment = (ReadStoryFragment) fragment;
                        String chapterNumber = readStoryFragment.getChapterNumber();

                        List<String> chapterNumbers = new ArrayList<>();

                        chapterNumbers.add(chapterNumber);

                        Bookmark bookmark = new Bookmark(user.getUid(), storyId, chapterNumbers);

                        BookmarkReposiitory bookmarkReposiitory = new BookmarkReposiitory();

                        bookmarkReposiitory.addBookmarkStory(bookmark);
                        Toast.makeText(detailStoryActivity, "Bạn đã đánh đấu thành công.!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }



    public void handleRatingStory(){
        topAppBar.setTitle("Đánh giá");

        FragmentTransaction fragmentTransaction = detailStoryActivity.fragmentManager.beginTransaction();

        RatingStoryFragment frag = RatingStoryFragment.newInstance(storyId);
        // search fragment deatil story;
        fragmentTransaction.replace(R.id.fragmentContainer, frag,"rating_story_fragment" );
        fragmentTransaction.addToBackStack("rating_story_fragment");
        fragmentTransaction.commit();


    }

    private void handleCommentStory() {
        topAppBar.setTitle("Bình luận");

        FragmentTransaction fragmentTransaction = detailStoryActivity.fragmentManager.beginTransaction();

        CommentStoryFragment frag = CommentStoryFragment.newInstance(storyId, storyTitle);
        // search fragment deatil story;
        fragmentTransaction.replace(R.id.fragmentContainer, frag,"comment_story_fragment" );
        fragmentTransaction.addToBackStack("comment_story_fragment");
        fragmentTransaction.commit();


    }

    public void handleFavoriteStory(){
        FavoriteRepository favoriteRepository = new FavoriteRepository();

        favoriteRepository.addFavorite(new Favorite(storyId, user.getUid()));
        Toast.makeText(detailStoryActivity, "Bạn đã yêu thích truyện thành công.!!", Toast.LENGTH_SHORT).show();
    }

}
