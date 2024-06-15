package com.example.comicword.ui.adapter_admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.AdminCategoryViewHolder> {
    private Map<String, Category> categoryMap;

    private OnSelectedItemListener onSelectedItemListener;

    public void setOnSelectedItemListener(OnSelectedItemListener onSelectedItemListener) {
        this.onSelectedItemListener = onSelectedItemListener;
    }

    public AdminCategoryAdapter(Map<String, Category> categoryMap) {
        this.categoryMap = categoryMap;
    }

    @NonNull
    @Override
    public AdminCategoryAdapter.AdminCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_admin, parent,false);

        return new AdminCategoryAdapter.AdminCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryAdapter.AdminCategoryViewHolder holder, int position) {
        List<Category> categoryList = new ArrayList<>(categoryMap.values());
        List<String> categoryIdList = new ArrayList<>(categoryMap.keySet());

        Category category = categoryList.get(position);
        String categoryId = categoryIdList.get(position);

        holder.txtCategoryAdmin.setText(category.getCategoryTitle());

        Log.i("CATEGORY_CATEGORY_ADMIN", "" + category.getCategoryTitle());
        Log.i("CATEGORY_CATEGORY_ADMIN", "" + category.getIsDelete());

        boolean nIsDelete =  category.getIsDelete();

        int selectedIndex = category.getIsDelete() == true ? 1 : 0;
        holder.spinnerCategoryDelete.setSelection(selectedIndex);

        holder.spinnerCategoryDelete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedIsDelete = holder.spinnerCategoryDelete.getSelectedItemPosition();

                boolean value = selectedIsDelete == 1 ? true : false;
                onSelectedItemListener.onSelectedItem(categoryId, value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryMap.size();
    }

    public class AdminCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCategoryAdmin;

        private Spinner spinnerCategoryDelete;

        public AdminCategoryViewHolder(View itemView) {
            super(itemView);

            txtCategoryAdmin = itemView.findViewById(R.id.txtCategoryAdmin);
            spinnerCategoryDelete = itemView.findViewById(R.id.spinnerCategoryDelete);
        }
    }

    public interface OnSelectedItemListener {
        void onSelectedItem(String categoryId, Boolean value);
    }

}
