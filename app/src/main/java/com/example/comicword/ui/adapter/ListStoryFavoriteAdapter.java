package com.example.comicword.ui.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comicword.R;
import com.example.comicword.data.model.Story;

import java.util.List;

public class ListStoryFavoriteAdapter extends RecyclerView.Adapter<ListStoryFavoriteAdapter.StoryViewHolder> {
    private List<Story> storyList;
    private List<String> IdList;

    private List<String> favoriteIds;
    private OnStoryClickListener storyClickListener;

    private OnImageUnfavoriteListener imageUnFavoriteListener;

    public ListStoryFavoriteAdapter(List<Story> storyList, List<String> IdList, List<String> favoriteIds) {
        this.IdList = IdList;
        this.storyList = storyList;
        this.favoriteIds = favoriteIds;
    }
    // Thêm setter cho storyClickListener
    public void setOnStoryClickListener(OnStoryClickListener listener) {
        this.storyClickListener = listener;
    }

    public void setImageUnFavoriteListener(OnImageUnfavoriteListener listener) {
        this.imageUnFavoriteListener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_story_favorite, parent, false);
        return new ListStoryFavoriteAdapter.StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        // Gọi phương thức bindData trong StoryViewHolder để thiết lập dữ liệu cho item tại vị trí position
        Story story = storyList.get(position);
        String id = IdList.get(position);
        String favoriteId = favoriteIds.get(position);

        String storyType = story.getStoryType();

        holder.textTitle.setText(story.getStoryTitle());
        holder.textAuthor.setText(story.getStoryAuthor());


        Glide.with(holder.itemView)
                .load(story.getSotryCoverImageUrl())
                .into(holder.coverImage);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyClickListener != null) {
                    // Truyền ID của document qua DetailStoryActivity
                    storyClickListener.onStoryClick(id, storyType, story.getStoryTitle());
                }
            }
        });

        holder.imgUnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUnFavoriteListener.onImageUnfavoriteCLick(favoriteId);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong danh sách
        return storyList == null ? 0 : storyList.size();
    }



    // Gộp ViewHolder vào trong Adapter
    public class StoryViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong layout story_item.xml
        private TextView textTitle;
        private ImageView coverImage;
        private TextView textAuthor;
        private ImageView imgUnfavorite;


        public StoryViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout
            textTitle = itemView.findViewById(R.id.titleStoryFavorite);
            coverImage = itemView.findViewById(R.id.imageStoryFavorite);
            textAuthor = itemView.findViewById(R.id.textAuthorFavorite);
            imgUnfavorite = itemView.findViewById(R.id.imgUnfavorite);
        }
    }
    public interface OnStoryClickListener {
        void onStoryClick(String storyId, String storyType,String storyTitle);
    }

    public interface OnImageUnfavoriteListener {
        void onImageUnfavoriteCLick(String favoriteId);
    }
}

