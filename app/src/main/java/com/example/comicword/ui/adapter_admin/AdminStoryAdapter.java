package com.example.comicword.ui.adapter_admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comicword.R;
import com.example.comicword.data.model.Story;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class AdminStoryAdapter extends RecyclerView.Adapter<AdminStoryAdapter.AdminStoryViewHolder> {

    private Map<String, Story> storyMap;
    private OnUpdateStoryClickListener onUpdateStoryClickListener;

    public void setOnUpdateStoryClickListener(OnUpdateStoryClickListener onUpdateStoryClickListener) {
        this.onUpdateStoryClickListener= onUpdateStoryClickListener;
    }

    public AdminStoryAdapter(Map<String, Story> storyMap) {
        this.storyMap = storyMap;
    }

    @NonNull
    @Override
    public AdminStoryAdapter.AdminStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_admin, parent, false);

        return new AdminStoryAdapter.AdminStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminStoryAdapter.AdminStoryViewHolder holder, int position) {
        List<Story> storyList = new ArrayList<>(storyMap.values());
        List<String> storyIdList = new ArrayList<>(storyMap.keySet());

        Story story = storyList.get(position);
        String storyId = storyIdList.get(position);

        holder.titleStory.setText("Tiêu đề : " + story.getStoryTitle());
        holder.textAuthor.setText("Tác giả : " + story.getStoryAuthor());
        holder.textCategory.setText("Mã thể loại : " + story.getCategoryId());
        holder.textDescription.setText(" Giới thiệu truyện : " + story.getStoryDescription());
        holder.textType.setText("Loại truyện : " + story.getStoryType());

        Glide.with(holder.itemView)
                .load(story.getSotryCoverImageUrl())
                .into(holder.imageStory);

        holder.imgUpdateStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateStoryClickListener.onUpdateStoryClick(storyId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyMap.size();
    }

    public class AdminStoryViewHolder extends RecyclerView.ViewHolder {
        private TextView titleStory, textType, textCategory, textDescription;
        private ImageView imageStory;
        private TextView textAuthor;
        private Spinner spinnerIsDelete;

        private ImageButton imgUpdateStory;
        public AdminStoryViewHolder(View itemView) {
            super(itemView);

            titleStory = itemView.findViewById(R.id.titleStory);
            imageStory = itemView.findViewById(R.id.imageStory);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textType = itemView.findViewById(R.id.textType);
            textCategory = itemView.findViewById(R.id.textCategory);
            textDescription = itemView.findViewById(R.id.textDescription);
            spinnerIsDelete = itemView.findViewById(R.id.spinnerIsDelete);
            imgUpdateStory = itemView.findViewById(R.id.imgUpdateStory);

        }
    }


    public interface OnUpdateStoryClickListener {
        void onUpdateStoryClick(String storyId);
    }
}
