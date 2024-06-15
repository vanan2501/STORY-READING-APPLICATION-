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
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.FavoriteRepository;
import com.example.comicword.data.repository.HistoryRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.adapter.ListStoryFavoriteAdapter;
import com.example.comicword.ui.adapter.ListStoryHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private final static String param1 = "userId";

    // TODO: Rename and change types of parameters
    private String userId;

    private StoryRepository storyRepository;
    private ListStoryHistoryAdapter listStoryHistoryAdapter;
    private RecyclerView recyclerView;
    private DetailStoryActivity detailStoryActivity;
    private MainActivity mainActivity;
    private HistoryRepository historyRepository;

    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance(String userId) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(param1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(param1);
        }

        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        setListStoryRycleView(view);

        return view;
    }

    private List<Story> newStoryList;
    private List<String> newIdList;
    private List<String> newHistoryDateTamps;

    public void setListStoryRycleView(View view){
        storyRepository = new StoryRepository();
        // constructor story adapter


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerHistoryFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        newStoryList = new ArrayList<>();
        newIdList = new ArrayList<>();
        newHistoryDateTamps = new ArrayList<>();

        listStoryHistoryAdapter = new ListStoryHistoryAdapter(newStoryList, newIdList, newHistoryDateTamps);

        recyclerView.setAdapter(listStoryHistoryAdapter);

        loadData(view);
    }


    private void loadData(View view) {
        historyRepository = new HistoryRepository();
        storyRepository = new StoryRepository();

        historyRepository.getListHistory(userId, new BaseRepository.OnDataFetchedListener<List<String>, List<String> >() {
            @Override
            public void onDataFetched(List<String> storyIds, List<String> historyDateTamps) {

                if(storyIds != null && !storyIds.isEmpty()) {
                    storyRepository.getListStoryWhereIn(storyIds, new BaseRepository.OnDataFetchedListener<List<Story>, List<String>>() {
                        @Override
                        public void onDataFetched(List<Story> storyList, List<String> idList) {

                            Log.i("HISTORY_FRAGMENT", storyList.toString());

                            storyList.forEach(story -> {
                                newStoryList.add(story);
                            });

                            idList.forEach(id -> {
                                newIdList.add(id);
                            });

                            historyDateTamps.forEach(s -> {
                                newHistoryDateTamps.add(s);
                            });

                            listStoryHistoryAdapter.notifyDataSetChanged();


                            listStoryHistoryAdapter.setOnStoryClickListener(new ListStoryHistoryAdapter.OnStoryClickListener() {
                                @Override
                                public void onStoryClick(String storyId, String storyType, String storyTitle) {
                                    Log.i("HISTORY_TAG", ""+ storyIds.toString());


                                    recyclerView.setAdapter(listStoryHistoryAdapter);
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
                        }
                    });
                } else {

                }

            }
        });

    }
}