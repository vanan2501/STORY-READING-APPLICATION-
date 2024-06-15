package com.example.comicword.ui.fragment_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.data.repository_admin.AdminBaseRepository;
import com.example.comicword.data.repository_admin.AdminStoryRepository;
import com.example.comicword.ui.adapter_admin.AdminCategoryAdapter;
import com.example.comicword.ui.adapter_admin.AdminStoryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;


public class StoryAdminFragment extends Fragment {



    public StoryAdminFragment() {
        // Required empty public constructor
    }

    public static StoryAdminFragment newInstance(String param1, String param2) {
        StoryAdminFragment fragment = new StoryAdminFragment();

        return fragment;
    }

    private RecyclerView recyclerView;

    private AdminStoryAdapter adminStoryAdapter;

    private Map<String, Story> newStoryMap;
    private AdminStoryRepository adminStoryRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newStoryMap = new HashMap<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story_admin, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategoryStoryFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);


        adminStoryAdapter = new AdminStoryAdapter(newStoryMap);

        recyclerView.setAdapter(adminStoryAdapter);

        loadData(view);
        setFloattingButton(view);
        return view;
    }

    public void setFloattingButton(View view){
        FloatingActionButton fb = view.findViewById(R.id.floating_action_button);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOverlayStory();
            }
        });
    }

    public void showOverlayStory(){
        OverlayStoryFragment overlay = new OverlayStoryFragment();
        overlay.show(getChildFragmentManager(), "OverlayStoryFragment");
    }

    public void loadData(View view){

        adminStoryRepository = new AdminStoryRepository();

        adminStoryRepository.getListSotry(new AdminBaseRepository.OnDataFetchedListener<Map<String, Story>, Boolean>() {
            @Override
            public void onDataFetched(Map<String, Story> storyMap, Boolean value) {
                storyMap.forEach((k,v ) -> {
                    newStoryMap.put(k, v);

                    adminStoryAdapter.notifyDataSetChanged();
                });
            }
        });

        adminStoryAdapter.setOnUpdateStoryClickListener(new AdminStoryAdapter.OnUpdateStoryClickListener() {
            @Override
            public void onUpdateStoryClick(String storyId) {
                Toast.makeText(view.getContext(),"sotryId : " + storyId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}