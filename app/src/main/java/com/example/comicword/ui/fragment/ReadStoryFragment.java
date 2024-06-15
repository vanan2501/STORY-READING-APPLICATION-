package com.example.comicword.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comicword.R;;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.ChapterRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.adapter.ImageChapterAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadStoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadStoryFragment extends Fragment {
    private TextView txtContentReadStory;
    private ImageChapterAdapter imageChapterAdapter;

    private RecyclerView recyclerViewConentReadStory;
    private String storyType;
    private String chapterContent;
    private String chapterId;
    private String chapterNumber;

    public ReadStoryFragment() {
        // Required empty public constructor
    }


    public static ReadStoryFragment newInstance(String storyType, String chapterContent,
                                                String chapterId, String chapterNumber) {
        ReadStoryFragment fragment = new ReadStoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("storyType", storyType);
        bundle.putSerializable("chapterContent",  chapterContent);
        bundle.putString("chapterId", chapterId);
        bundle.putString("chapterNumber", chapterNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            storyType = getArguments().getString("storyType");
            chapterContent = getArguments().getString("chapterContent");
            chapterId = getArguments().getString("chapterId");
            setChapterNumber(getArguments().getString("chapterNumber"));
        }
        setHasOptionsMenu(true);
        ((DetailStoryActivity) getActivity()).detailStoryController.setChangeToolBar("Chapter " + getChapterNumber().substring(7,8 ));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_story, container, false);


        if(getArguments() != null){
            if(storyType.contains("text")) {
                txtContentReadStory = view.findViewById(R.id.txtContentReadStory);
                txtContentReadStory.setVisibility(View.VISIBLE);

                txtContentReadStory.setText(formatParagraphs(chapterContent));
            } else {

                recyclerViewConentReadStory = view.findViewById(R.id.recyclerViewConentReadStory);

                recyclerViewConentReadStory.setVisibility(View.VISIBLE);

                recyclerViewConentReadStory = (RecyclerView) view.findViewById(R.id.recyclerViewConentReadStory);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);
                recyclerViewConentReadStory.setLayoutManager(gridLayoutManager);

                loadImage(chapterId, getChapterNumber());
            }
        }

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bookmark_app_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.comment).setVisible(false).setShowAsAction(0);
        menu.findItem(R.id.favorite).setVisible(false).setShowAsAction(0);
        menu.findItem(R.id.rating).setVisible(false).setShowAsAction(0);

    }

    public void loadImage(String chapterId, String chapterNumber){

        ChapterRepository chapterRepository = new ChapterRepository();


        chapterRepository.getChapterContentUrl(new BaseRepository.OnDataFetchedListener<List<String>, String>() {
            @Override
            public void onDataFetched(List<String> data, String value) {

                imageChapterAdapter = new ImageChapterAdapter(data);

                recyclerViewConentReadStory.setAdapter(imageChapterAdapter);
            }
        }, chapterId, chapterNumber);



    }

    public String formatParagraphs(String data) {
        StringBuilder formattedText = new StringBuilder();
        String[] paragraphs = data.split("\\n");

        for (String paragraph : paragraphs) {
            String[] sentences = paragraph.split("\\.");
            for (String sentence : sentences) {
                formattedText.append(sentence.trim()).append(".\n\n");
            }
            formattedText.append("\n");
        }

        return formattedText.toString();
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
}