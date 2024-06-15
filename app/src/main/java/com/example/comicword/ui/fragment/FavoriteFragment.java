package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Favorite;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.FavoriteRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.adapter.ListStoryAdapter;
import com.example.comicword.ui.adapter.ListStoryFavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private final static String param1 = "userId";
    public FavoriteFragment() {
        // Required empty public constructor
    }

    private String userId;

    private StoryRepository storyRepository;
    private ListStoryFavoriteAdapter listStoryFavoriteAdapter;
    private RecyclerView recyclerView;
    private DetailStoryActivity detailStoryActivity;
    private MainActivity mainActivity;
    private FavoriteRepository favoriteRepository;

    private List<Story> newStoryList;
    private List<String> newStoryListIds;
    private List<String> newFavoriteIds;

    public static FavoriteFragment newInstance(String userId) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(param1, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            userId = getArguments().getString(param1);
        }

        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        setListStoryRycleView(view);

        return view;
    }

    public void setListStoryRycleView(View view){
        storyRepository = new StoryRepository();
        // constructor story adapter


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFavoriteFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        this.newStoryList = new ArrayList<>();
        this.newStoryListIds = new ArrayList<>();
        this.newFavoriteIds = new ArrayList<>();

        listStoryFavoriteAdapter = new ListStoryFavoriteAdapter(newStoryList, newStoryListIds, newFavoriteIds);

        recyclerView.setAdapter(listStoryFavoriteAdapter);

        loadData(view);
    }

    private void loadData(View view) {
        favoriteRepository = new FavoriteRepository();
        storyRepository = new StoryRepository();

        favoriteRepository.getListFavorite(userId, new BaseRepository.OnDataFetchedListener<List<String>, List<String>>() {
            @Override
            public void onDataFetched(List<String> storyIds, List<String> favoriteIds) {

                if(storyIds != null && !storyIds.isEmpty()) {
                    storyRepository.getListStoryWhereIn(storyIds, new BaseRepository.OnDataFetchedListener<List<Story>, List<String>>() {
                        @Override
                        public void onDataFetched(List<Story> storyList, List<String> storyListIds) {

                            for(Story s : storyList){
                                newStoryList.add(s);
                            }

                            for(String id : storyListIds){
                                newStoryListIds.add(id);
                            }

                            for(String id : favoriteIds){
                                newFavoriteIds.add(id);
                            }
                            listStoryFavoriteAdapter.notifyDataSetChanged();


                            listStoryFavoriteAdapter.setOnStoryClickListener(new ListStoryFavoriteAdapter.OnStoryClickListener() {
                                @Override
                                public void onStoryClick(String storyId,String storyType, String storyTitle) {

                                    if(mainActivity != null) {
                                        detailStoryActivity = new DetailStoryActivity();

                                        Intent i  = new Intent(mainActivity, detailStoryActivity.getClass());

                                        i.putExtra("storyId", storyId);
                                        i.putExtra("storyType", storyType);
                                        i.putExtra("storyTitle", storyTitle);
                                        mainActivity.startActivity(i);
                                    }

                                }
                            });

                            listStoryFavoriteAdapter.setImageUnFavoriteListener(new ListStoryFavoriteAdapter.OnImageUnfavoriteListener() {
                                @Override
                                public void onImageUnfavoriteCLick(String favoriteId) {

                                    int indexToRemove = -1;

                                    for(int i = 0; i < favoriteIds.size(); i++){

                                        if(favoriteIds.get(i).equals(favoriteId)) {
                                            indexToRemove = i;
                                            break;
                                        }
                                    }

                                    if(indexToRemove != -1) {
                                        newFavoriteIds.remove(indexToRemove);
                                        newStoryList.remove(indexToRemove);
                                        newStoryListIds.remove(indexToRemove);

                                        listStoryFavoriteAdapter.notifyDataSetChanged();
                                    }

                                    favoriteRepository.deleteFavorite(favoriteId);

                                    Toast.makeText(view.getContext(), "Bạn đã hủy yêu thích thành công.!!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }
            }
        });

    }
}