    package com.example.comicword.ui.adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.comicword.R;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;

    public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
        private Map<String, Object> chapterContent;
        private OnChapterClickListener chapterClickListener;

        public ChapterAdapter(){

        }

        public ChapterAdapter(Map<String, Object> chapterContent) {
            this.chapterContent = chapterContent;
        }

        public void setOnChapterClickListener(OnChapterClickListener listener) {
            this.chapterClickListener = listener;
        }

        @NonNull
        @Override
        public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
            return new ChapterAdapter.ChapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
           /////
            List<String> chapterKeys = new ArrayList<>(chapterContent.keySet());
            List<Object> chapterValues = new ArrayList<>(chapterContent.values());
            /////
            String chapterKey = chapterKeys.get(position);
            Object chapterValue = chapterValues.get(position);
            ////

            String chapterId = "Chapter "+ chapterKey.substring(7,8).toString();

            holder.chapterTitleTextView.setText(chapterId);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chapterClickListener != null) {
                        chapterClickListener.onChapterClick(chapterValue, chapterKey);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return chapterContent.size();
        }


        ////
        public class ChapterViewHolder extends RecyclerView.ViewHolder {
            private TextView chapterTitleTextView;

            public ChapterViewHolder(View itemView) {
                super(itemView);
                chapterTitleTextView = itemView.findViewById(R.id.chapterTitleTextView);
            }
        }

        public interface OnChapterClickListener {
            void onChapterClick(Object data,String chapterNumber);
        }
    }

