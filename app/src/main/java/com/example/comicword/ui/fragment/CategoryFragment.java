package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.CategoryRepository;
import com.example.comicword.data.repository.HistoryRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.adapter.CategoryAdapter;
import com.example.comicword.ui.adapter.ListStoryHistoryAdapter;

import java.util.List;

public class CategoryFragment extends Fragment {



    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    private CategoryAdapter categoryAdapter;
    private CategoryRepository categoryRepository;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);

        setListStoryRycleView(view);

        return view;
    }

    public void setListStoryRycleView(View view){

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategoryFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        loadData(view);
    }

    private void loadData(View view) {
        categoryRepository = new CategoryRepository();

        categoryRepository.getListCategory(new BaseRepository.OnDataFetchedListener<List<Category>, List<String>>() {
            @Override
            public void onDataFetched(List<Category> categoryList, List<String> categoryIdList) {

                Log.i("CATEGORY_FRAGMENT_TAG", " " + categoryList.toString());
                categoryAdapter = new CategoryAdapter(categoryList, categoryIdList );
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.setCategoryCLickListener(new CategoryAdapter.OnCategoryCLickListener() {
                    @Override
                    public void categoryCLick(String categoryId, String categoryTitle) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                        CategoryStoryFragment frag = CategoryStoryFragment.newInstance(categoryId, categoryTitle);

                        fragmentTransaction.replace(R.id.fragmentContainer, frag,"category_story_fragment");
                        fragmentTransaction.addToBackStack("category_story_fragment");
                        fragmentTransaction.commit();
                    }
                });


            }
        });

    }
}