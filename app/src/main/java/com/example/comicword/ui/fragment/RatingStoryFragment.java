package com.example.comicword.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comicword.R;
import com.example.comicword.data.model.Rating;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.RatingRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RatingStoryFragment extends Fragment {

    private final static String param1 = "storyId";
    private String storyId;

    private  StoryRepository storyRepository;
    private RatingRepository ratingRepository;
    private DetailStoryActivity detailStoryActivity;

    private   FirebaseUser user;
    public RatingStoryFragment() {
        // Required empty public constructor
    }

    public static RatingStoryFragment newInstance(String storyId) {
        RatingStoryFragment fragment = new RatingStoryFragment();
        Bundle args = new Bundle();
        args.putString(param1, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            storyId = getArguments().getString(param1);
        }

        storyRepository = new StoryRepository();
        ratingRepository = new RatingRepository();
        detailStoryActivity = new DetailStoryActivity();

        user = detailStoryActivity.user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_rating_story, container, false);


        loadDataStroryRating(view);

        handleRatingStory(view);

        return view;
    }

    private void handleRatingStory(View view) {
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar); // Đổi ID tương ứng

        setRating(ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int intValue = Math.round(rating);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();

                Rating nRating = new Rating(mUser.getUid(), intValue,  storyId, getCurrentDateTime());

                ratingRepository.addRatingStory(nRating, user.getUid());


            }
        });


    }

    public void setRating(RatingBar ratingBar){



        ratingRepository.getRatingStory(storyId, user.getUid(), new BaseRepository.OnDataFetchedListener<Integer, Integer>() {

            @Override
            public void onDataFetched(Integer data, Integer value) {
                ratingBar.setRating(data);
            }
        });
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private void loadDataStroryRating(View view) {

        ImageView imageView = (ImageView) view.findViewById(R.id.imageStoryRating);
        TextView textView = (TextView) view.findViewById(R.id.titleStoryRating);




        storyRepository.getStoryById(storyId, new BaseRepository.OnDataFetchedListener<Story, Object>() {

            @Override
            public void onDataFetched(Story story, Object value) {
                Glide.with(view.getContext())
                        .load(story.getSotryCoverImageUrl())
                        .into(imageView);

                textView.setText(story.getStoryTitle());

            }

        });

    }
}