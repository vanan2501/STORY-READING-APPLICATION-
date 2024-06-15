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

public class ListStoryHistoryAdapter extends RecyclerView.Adapter<ListStoryHistoryAdapter.StoryViewHolder> {
    private List<Story> storyList;
    private List<String> IdList;
    private List<String> historyTimeTamps;

    private OnStoryClickListener storyClickListener;


    public ListStoryHistoryAdapter(List<Story> storyList, List<String> IdList, List<String> historyTimeTamps) {
        this.storyList = storyList;
        this.IdList = IdList;
        this.historyTimeTamps = historyTimeTamps;
    }

    // Thêm setter cho storyClickListener
    public void setOnStoryClickListener(OnStoryClickListener listener) {
        this.storyClickListener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_story_history, parent, false);
        return new ListStoryHistoryAdapter.StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        // Gọi phương thức bindData trong StoryViewHolder để thiết lập dữ liệu cho item tại vị trí position
        Story story = storyList.get(position);
        String id = IdList.get(position);
        String historyTimeTamp = historyTimeTamps.get(position);

        String storyType = story.getStoryType();

        holder.textTitle.setText(story.getStoryTitle());
        holder.textAuthor.setText(story.getStoryAuthor());


        Glide.with(holder.itemView)
                .load(story.getSotryCoverImageUrl())
                .into(holder.coverImage);


        holder.textDateTime.setText("ngày : " + historyTimeTamp.substring(0, 10) + "   giờ " + historyTimeTamp.substring(11));


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
        private TextView textDateTime;


        public StoryViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout
            textTitle = itemView.findViewById(R.id.titleStoryHistory);
            coverImage = itemView.findViewById(R.id.imageStoryHistory);
            textAuthor = itemView.findViewById(R.id.textAuthorHistory);
            textDateTime = itemView.findViewById(R.id.txtDateTimeHistory);
        }
    }
    public interface OnStoryClickListener {
        void onStoryClick(String storyId, String storyType,String storyTitle);
    }
}

