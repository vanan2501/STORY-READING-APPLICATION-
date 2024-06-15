package com.example.comicword.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicword.R;
import com.example.comicword.data.model.Bookmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<String> chapterNumbers;

    private BookmarkAdapter.OnChapterClickListener chapterClickListener;

    public BookmarkAdapter(List<String> chapterNumbers){
        this.chapterNumbers = chapterNumbers;

    }

    public void setOnChapterClickListener(BookmarkAdapter.OnChapterClickListener listener) {
        this.chapterClickListener = listener;
    }


    @NonNull
    @Override
    public BookmarkAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);

        return new BookmarkAdapter.BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.BookmarkViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String chapterNumber = chapterNumbers.get(position);
        holder.txtChapterNumber.setText(chapterNumber);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapterClickListener != null) {
                    chapterClickListener.onChapterClick(chapterNumber);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterNumbers.size();
    }



    public class BookmarkViewHolder extends RecyclerView.ViewHolder {

        private TextView txtChapterNumber;

        public BookmarkViewHolder(View itemView) {
            super(itemView);


            txtChapterNumber = itemView.findViewById(R.id.txtChapterNumber);

        }

    }

    public interface OnChapterClickListener {
        void onChapterClick(String data);
    }
}
