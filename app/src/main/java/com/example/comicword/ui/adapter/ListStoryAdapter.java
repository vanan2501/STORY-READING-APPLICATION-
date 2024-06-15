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

public class ListStoryAdapter extends RecyclerView.Adapter<ListStoryAdapter.StoryViewHolder> {
    private List<Story> storyList;
    private List<String> IdList;
    private OnStoryClickListener storyClickListener;

    public ListStoryAdapter(List<Story> storyList, List<String> IdList) {
        this.IdList = IdList;
        this.storyList = storyList;
    }
    // Thêm setter cho storyClickListener
    public void setOnStoryClickListener(OnStoryClickListener listener) {
        this.storyClickListener = listener;
    }


    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_story, parent, false);
        return new ListStoryAdapter.StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        // Gọi phương thức bindData trong StoryViewHolder để thiết lập dữ liệu cho item tại vị trí position
        Story story = storyList.get(position);
        String id = IdList.get(position);
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


        public StoryViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout
            textTitle = itemView.findViewById(R.id.titleStory);
            coverImage = itemView.findViewById(R.id.imageStory);
            textAuthor = itemView.findViewById(R.id.textAuthor);
        }
    }
        public interface OnStoryClickListener {
            void onStoryClick(String storyId, String storyType,String storyTitle);
        }
}

