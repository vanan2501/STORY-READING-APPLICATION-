package com.example.comicword.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comicword.R;

import java.util.List;

public class ImageChapterAdapter extends RecyclerView.Adapter<ImageChapterAdapter.ImageChapterViewHolder> {

    private List<String> listImageUrl;

    public ImageChapterAdapter(List<String> listImageUrl){
        this.listImageUrl = listImageUrl;
    }

    @NonNull
    @Override
    public ImageChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_chapter, parent, false);
        return new ImageChapterAdapter.ImageChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageChapterAdapter.ImageChapterViewHolder holder, int position) {

        String url = listImageUrl.get(position);

        Glide.with(holder.itemView)
                .load(url.toString())
                .override(750, 1200)
                .into(holder.imageViewChapterItem);
    }

    @Override
    public int getItemCount() {
        return listImageUrl.size();
    }

    public class ImageChapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewChapterItem;
        public ImageChapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewChapterItem = itemView.findViewById(R.id.imageViewChapterItem);
        }
    }
}
