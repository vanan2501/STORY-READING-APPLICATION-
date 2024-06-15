package com.example.comicword.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;
;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private List<String> categoryIdList;

    private OnCategoryCLickListener categoryCLickListener;

    public CategoryAdapter(List<Category> categoryList, List<String> categoryIdList) {
        this.categoryList = categoryList;
        this.categoryIdList = categoryIdList;

    }

    public void setCategoryCLickListener(OnCategoryCLickListener categoryCLickListener) {
        this.categoryCLickListener = categoryCLickListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        String categoryId = categoryIdList.get(position);


        holder.txtCategory.setText(category.getCategoryTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryCLickListener.categoryCLick(categoryId, category.getCategoryTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCategory;

        public CategoryViewHolder(View itemView){
            super(itemView);

            txtCategory = itemView.findViewById(R.id.txtCategory);

        }

    }

    public interface OnCategoryCLickListener{
        void categoryCLick(String categoryId, String categoryTitle);
    }
}
