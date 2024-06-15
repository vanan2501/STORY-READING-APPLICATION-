package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.Bookmark;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.BookmarkReposiitory;
import com.example.comicword.data.repository.ChapterRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.adapter.BookmarkAdapter;
import com.example.comicword.ui.adapter.ListStoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BookmarkFragment extends Fragment {


    private static final String param1 = "storyId";
    private static final String param2 = "userId";
    private static final String param3 = "storyType";
    private static final String param4 = "chapterId";
    private static final String param5 = "chapterContent";

    private String storyId;
    private String userId;

    private String storyType;
    private String chapterId;
    private String chapterContent;

    private BookmarkReposiitory bookmarkReposiitory;
    private BookmarkAdapter bookmarkAdapter;

    private List<String> chapterList;

    private DetailStoryActivity detailStoryActivity;
    public BookmarkFragment() {
        // Required empty public constructor
    }



    public static BookmarkFragment newInstance(String storyId, String userId,String storyType, String chapterId, String chapterContent) {
        BookmarkFragment fragment = new BookmarkFragment();
        Bundle args = new Bundle();
        args.putString(param1, storyId);
        args.putString(param2, userId);
        args.putString(param3, storyType);
        args.putString(param4, chapterId);
        args.putString(param5, chapterContent);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storyId = getArguments().getString(param1);
            userId = getArguments().getString(param2);
            storyType = getArguments().getString(param3);
            chapterId = getArguments().getString(param4);
            chapterContent = getArguments().getString(param5);
        }

        detailStoryActivity = new DetailStoryActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        setListStoryRycleView(view);

        return view;
    }

    public void setChapterReaded(Integer value, View view){
        TextView txtChapterReaded = view.findViewById(R.id.txtChapterReaded);
        TextView txtChapterReadContinue = view.findViewById(R.id.txtChapterReadContinue);

        txtChapterReaded.setText( "Đã đọc " + value + " chap.!!");

        if(value != null && value != 0) {
            txtChapterReadContinue.setVisibility(View.VISIBLE);
        }
    }


    public void setListStoryRycleView(View view){
        bookmarkReposiitory = new BookmarkReposiitory();
        // constructor story adapter


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewBookmark);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        chapterList = new ArrayList<>();

        bookmarkAdapter = new BookmarkAdapter(chapterList);
        recyclerView.setAdapter(bookmarkAdapter);
        loadData(view);

    }

    private void loadData(View view) {
        bookmarkReposiitory = new BookmarkReposiitory();

        bookmarkReposiitory.getBookmark(userId, storyId, new BaseRepository.OnDataFetchedListener<List<String>, Integer>() {
            @Override
            public void onDataFetched(List<String> chapterNumbers, Integer value) {

                if(value == 1) {

                    for(String s : sortList(chapterNumbers)) {
                        chapterList.add(s);
                    }

                    setChapterReaded(chapterNumbers.size(), view);


                    bookmarkAdapter.notifyDataSetChanged();

                    bookmarkAdapter.setOnChapterClickListener(new BookmarkAdapter.OnChapterClickListener() {
                        @Override
                        public void onChapterClick(String data) {

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                            ReadStoryFragment frag = ReadStoryFragment.newInstance(storyType,chapterContent , chapterId, data);
                            // search fragment deatil story;
                            fragmentTransaction.replace(R.id.fragmentContainer, frag,"read_story_fragment" );
                            fragmentTransaction.addToBackStack("read_story_fragment");
                            fragmentTransaction.commit();
                        }
                    });

                } else {
                    chapterList = null;
                }

            }
        });
    }


    public List<String> sortList(List<String> chapterNumbers){
        return chapterNumbers.stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    public Map<String, Boolean> sortMap(Map<String, Boolean> data){
        Map<String, Boolean> sortedMap = data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(
                        LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll
                );

        return sortedMap;
    }
}