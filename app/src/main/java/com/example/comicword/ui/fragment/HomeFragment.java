package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.adapter.ListStoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ListStoryAdapter listStoryAdapter;
    private StoryRepository storyRepository;
    private RecyclerView recyclerView;
    private DetailStoryActivity detailStoryActivity;
    private MainActivity mainActivity;

    private List<Story> newStoryList;
    private List<String> newStoryIdList;

    private List<Story> saveStoryList;

    private List<String> saveStoryIdList;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        setSearchView(mainActivity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setListStoryRycleView(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void setListStoryRycleView(View view){
        storyRepository = new StoryRepository();
        // constructor story adapter


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewContainer);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        newStoryList = new ArrayList<>();
        newStoryIdList = new ArrayList<>();
        saveStoryList = new ArrayList<>();
        saveStoryIdList = new ArrayList<>();

        listStoryAdapter = new ListStoryAdapter(newStoryList, newStoryIdList);
        recyclerView.setAdapter(listStoryAdapter);
        loadData();
    }

    private void loadData() {
        storyRepository.getStories(new BaseRepository.OnDataFetchedListener<List<Story>,List<String>>() {
            @Override
            public void onDataFetched(List<Story> data, List<String> id) {
                if(!data.isEmpty())
                {
                    saveStoryList.addAll(data);
                    saveStoryIdList.addAll(id);

                    // Đăng ký sự kiện click
                    data.forEach(story -> {
                        newStoryList.add(story);
                    });

                    id.forEach(v -> {
                        newStoryIdList.add(v);
                    });

                    listStoryAdapter.notifyDataSetChanged();

                    listStoryAdapter.setOnStoryClickListener(new ListStoryAdapter.OnStoryClickListener() {
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




                } else {
                    Toast.makeText(mainActivity, "No data available", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    public void setSearchView(MainActivity mainActivity){
        SearchView searchView = mainActivity.findViewById(R.id.searchViewStory);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(searchView);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textChangeSearch(newText);
                return false;
            }
        });

    }

    private void textChangeSearch(String newText) {

        newText = newText.toLowerCase(Locale.getDefault());
        newStoryList.clear();
        newStoryIdList.clear();

        if (newText.length() != 0) {
            for (int i = 0; i < saveStoryList.size(); i++) {
                if (saveStoryList.get(i).getStoryTitle().toLowerCase(Locale.getDefault()).contains(newText)) {
                    newStoryList.add(saveStoryList.get(i));
                    newStoryIdList.add(saveStoryIdList.get(i));
                }
            }
        } else {
            newStoryList.addAll(saveStoryList);
            newStoryIdList.addAll(saveStoryIdList);
        }

        listStoryAdapter.notifyDataSetChanged();
    }

    private void performSearch(SearchView searchView) {
        searchView.clearFocus();
    }

}