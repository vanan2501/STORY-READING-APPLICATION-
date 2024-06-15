package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.CategoryRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.activity.MainActivity;
import com.example.comicword.ui.adapter.CategoryAdapter;
import com.example.comicword.ui.adapter.ListStoryAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class CategoryStoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String param1 = "categoryId";
    private static final String param2 = "categoryTitle";

    // TODO: Rename and change types of parameters
    private String categoryId;

    private String categoryTitle;
    private StoryRepository storyRepository;
    private ListStoryAdapter listStoryAdapter;
    private RecyclerView recyclerView;
    private DetailStoryActivity detailStoryActivity;
    private MainActivity mainActivity;
    private TextView txtTitleCategoryStory;

    public CategoryStoryFragment() {
        // Required empty public constructor
    }

    public static CategoryStoryFragment newInstance(String categoryId, String categoryTitle) {
        CategoryStoryFragment fragment = new CategoryStoryFragment();
        Bundle args = new Bundle();
        args.putString(param1, categoryId);
        args.putString(param2, categoryTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(param1);
            categoryTitle = getArguments().getString(param2);
        }
        setHasOptionsMenu(true);

        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_story, container, false);

        setListStoryRycleView(view);

        return view;
    }

    public void setListStoryRycleView(View view){
        txtTitleCategoryStory = view.findViewById(R.id.txtTitleCategoryStory);
        txtTitleCategoryStory.setText(categoryTitle);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategoryStoryFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        loadData(view);
    }

    private void loadData(View view) {
        storyRepository = new StoryRepository();

        storyRepository.getListStoryOfCategory( categoryId, new BaseRepository.OnDataFetchedListener<List<Story>, List<String>>() {
            @Override
            public void onDataFetched(List<Story> storyList, List<String> storyIdList) {

                listStoryAdapter = new ListStoryAdapter(storyList, storyIdList );

                recyclerView.setAdapter(listStoryAdapter);

                listStoryAdapter.setOnStoryClickListener(new ListStoryAdapter.OnStoryClickListener() {
                    @Override
                    public void onStoryClick(String storyId, String storyType, String storyTitle) {
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

    }
}