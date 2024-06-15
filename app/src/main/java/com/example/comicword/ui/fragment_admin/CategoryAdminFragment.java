package com.example.comicword.ui.fragment_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;
import com.example.comicword.data.repository_admin.AdminBaseRepository;
import com.example.comicword.data.repository_admin.AdminCategoryRepository;
import com.example.comicword.ui.adapter_admin.AdminCategoryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class CategoryAdminFragment extends Fragment implements OverlayDialogFragment.OnCategoryAddedListener{

    public CategoryAdminFragment() {
        // Required empty public constructor
    }


    public static CategoryAdminFragment newInstance() {
        CategoryAdminFragment fragment = new CategoryAdminFragment();
        return fragment;
    }


    private RecyclerView recyclerView;
    public AdminCategoryAdapter adminCategoryAdapter;
    private Map<String, Category> newCategoryMap;
    private AdminCategoryRepository adminCategoryRepository;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.newCategoryMap = new HashMap<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_admin, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCategoryAdminFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);


        adminCategoryAdapter = new AdminCategoryAdapter(newCategoryMap);

        recyclerView.setAdapter(adminCategoryAdapter);

        loadData(view);


        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hiển thị lớp phủ khi nhấn vào nút floating action button
                showOverlay();
            }
        });


        return view;
    }

    private void showOverlay() {
        // Tạo DialogFragment với layout_overlay.xml và hiển thị nó
        OverlayDialogFragment overlayFragment = new OverlayDialogFragment();
        overlayFragment.show(getChildFragmentManager(), "OverlayDialog");
    }
    public void loadData(View view) {

        adminCategoryRepository = new AdminCategoryRepository();
        adminCategoryRepository.getListCategory(new AdminBaseRepository.OnDataFetchedListener<Map<String, Category>, Boolean>() {
            @Override
            public void onDataFetched(Map<String, Category> categoryMap, Boolean value) {
                categoryMap.forEach((k, v) -> {
                    newCategoryMap.put(k, v);
                });

                adminCategoryAdapter.notifyDataSetChanged();
            }
        });


        adminCategoryAdapter.setOnSelectedItemListener(new AdminCategoryAdapter.OnSelectedItemListener() {
            @Override
            public void onSelectedItem(String categoryId, Boolean value) {
                adminCategoryRepository.updateIsDelete(categoryId, value);
            }
        });

    }

    @Override
    public void onCategoryAdded(Category category) {
        adminCategoryRepository.addCategory(category, new AdminBaseRepository.OnDataFetchedListener<Category, String>() {
            @Override
            public void onDataFetched(Category newCategory, String id) {
                newCategoryMap.put(id, newCategory);

                adminCategoryAdapter.notifyDataSetChanged();
            }
        });


    }
}