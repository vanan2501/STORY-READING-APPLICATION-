package com.example.comicword.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import com.bumptech.glide.Glide;
import com.example.comicword.R;
import com.example.comicword.controller.DetailStoryController;
import com.example.comicword.data.model.Bookmark;
import com.example.comicword.data.model.Category;
import com.example.comicword.data.model.History;
import com.example.comicword.data.model.Story;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.BookmarkReposiitory;
import com.example.comicword.data.repository.CategoryRepository;
import com.example.comicword.data.repository.ChapterRepository;
import com.example.comicword.data.repository.HistoryRepository;
import com.example.comicword.data.repository.StoryRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.adapter.ChapterAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class DetailStoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter = null;
    private ChapterRepository chapterRepository;
    private StoryRepository storyRepository;
    private HistoryRepository historyRepository;
    private CategoryRepository categoryRepository;
    private TextView txtTitleStory;
    private TextView txtAuthorStory;
    private TextView txtCategoryStory;
    private ShapeableImageView imgCoverStory;

    private static final String ARG_PARAM1 = "storyId";
    private static final String ARG_PARAM2 = "storyType";
    private String storyId;
    private String storyType;

    private String storyDescription;
    public DetailStoryFragment() {
        // Required empty public constructor
    }

    public static DetailStoryFragment newInstance(String storyId,String storyType) {
        DetailStoryFragment fragment = new DetailStoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, storyId);
        args.putString(ARG_PARAM2, storyType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            storyId = getArguments().getString(ARG_PARAM1);
            storyType = getArguments().getString(ARG_PARAM2);
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_story, container, false);

        this.txtTitleStory = (TextView) view.findViewById(R.id.txtTitleStory);
        this.txtAuthorStory = (TextView) view.findViewById(R.id.txtAuthorStory);
        this.txtCategoryStory = (TextView) view.findViewById(R.id.txtCategoryStory);
        this.imgCoverStory = (ShapeableImageView) view.findViewById(R.id.imgCoverStory);
        this.chapterAdapter = new ChapterAdapter();

        setLayoutRecyclerChapter(storyId, view);
        RenderDetailStory(storyId, view);
        setButtonBookmark(view);
        setButtonReadFromBeging(view);
        setTabLout(view);
        return view;
    }


    public void setTabLout(View view){
        TabLayout tabLayoutDetailStory = view.findViewById(R.id.tabLayoutDetailStory);
        RecyclerView recyclerViewChaperList = view.findViewById(R.id.recyclerViewChaperList);
        TextView txtViewIntroduceStory = view.findViewById(R.id.txtViewIntroduceStory);


        tabLayoutDetailStory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn
                int selectedPosition = tab.getPosition(); // Vị trí của tab được chọn
                switch (selectedPosition) {
                    case 0:
                        txtViewIntroduceStory.setVisibility(View.GONE);
                        recyclerViewChaperList.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        recyclerViewChaperList.setVisibility(View.GONE);
                        txtViewIntroduceStory.setVisibility(View.VISIBLE);
                        txtViewIntroduceStory.setText(storyDescription);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Xử lý khi tab bị bỏ chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public void setButtonReadFromBeging(View view){
        Button btnReadFromBeging = (Button) view.findViewById(R.id.btnReadFromBeging);

        btnReadFromBeging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chapterRepository.getChaptersByStoryId(new BaseRepository.OnDataFetchedListener<Map<String, Object>, String>() {
                    @Override
                    public void onDataFetched(Map<String, Object> data, String chapterId) {

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                        String chapterContent = sortMap(data).get("chapter1").toString().replace("[", "").replace("]", "");

                        ReadStoryFragment frag = ReadStoryFragment.newInstance(storyType, chapterContent.toString(), chapterId, "chapter1");

                        fragmentTransaction.replace(R.id.fragmentContainer, frag,"read_story_fragment");
                        fragmentTransaction.addToBackStack("read_story_fragment");
                        fragmentTransaction.commit();
                    }
                }, storyId);
            }
        });
    }




    public void setButtonBookmark(View view){
        Button btnBookmark = (Button) view.findViewById(R.id.btnBookmark);


        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chapterRepository.getChaptersByStoryId(new BaseRepository.OnDataFetchedListener<Map<String, Object>, String>() {
                    @Override
                    public void onDataFetched(Map<String, Object> data, String chapterId) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                        BookmarkFragment frag;

                        String chapterContent = sortMap(data).values().toString().replace("[", "").replace("]", "");

                        frag = BookmarkFragment.newInstance(storyId, user.getUid(), storyType, chapterId, chapterContent);

                        fragmentTransaction.replace(R.id.fragmentContainer, frag,"read_story_fragment");
                        fragmentTransaction.addToBackStack("read_story_fragment");
                        fragmentTransaction.commit();
                    }
                }, storyId);
            }
        });
    }

    public void setLayoutRecyclerChapter(String storyId, View view) {

        chapterRepository = new ChapterRepository();
////
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChaperList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadData(storyId);
    }


    private void loadData(String storyId) {

        chapterRepository.getChaptersByStoryId(new BaseRepository.OnDataFetchedListener<Map<String, Object>, String>() {
            @Override
            public void onDataFetched(Map<String, Object> data, String chapterId) {

                chapterAdapter = new ChapterAdapter(sortMap(data));

                chapterAdapter.setOnChapterClickListener(new ChapterAdapter.OnChapterClickListener() {
                    @Override
                    public void onChapterClick(Object chapterContent, String chapterNumber) {

                        ((DetailStoryActivity) getActivity()).detailStoryController.setChangeToolBar("Chapter " + chapterNumber.substring(7,8 ));

                        handleSwitchToReadStory(chapterContent, chapterId, chapterNumber);

                        handleAddStoryToHistory(storyId);


                    }
                });
                recyclerView.setAdapter(chapterAdapter);
            }

        }, storyId);
    }


    public void handleSwitchToReadStory(Object chapterContent, String chapterId, String chapterNumber){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ReadStoryFragment frag = ReadStoryFragment.newInstance(storyType, chapterContent.toString(), chapterId, chapterNumber);

        fragmentTransaction.replace(R.id.fragmentContainer, frag,"read_story_fragment");
        fragmentTransaction.addToBackStack("read_story_fragment");
        fragmentTransaction.commit();

    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private void handleAddStoryToHistory(String storyId) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        historyRepository = new HistoryRepository();

        if(mUser != null){
            History history = new History(mUser.getUid(), storyId, getCurrentDateTime());

            historyRepository.addHistoryStory(history);
        } else {
            Log.i("DETAIL_STORY_TAG", "user id null");
        }
    }

    public void RenderDetailStory(String storyId, View view){
        storyRepository = new StoryRepository();

        storyRepository.getStoryById(storyId, new BaseRepository.OnDataFetchedListener<Story, String>() {
            @Override
            public void onDataFetched(Story story, String value) {

                txtTitleStory.setText(story.getStoryTitle());
                txtAuthorStory.setText(story.getStoryAuthor());

                categoryRepository = new CategoryRepository();

                categoryRepository.getTitleCategory(story.getCategoryId(), new BaseRepository.OnDataFetchedListener<Category, String>() {
                    @Override
                    public void onDataFetched(Category cate, String value) {
                        txtCategoryStory.setText(cate.getCategoryTitle());
                    }
                });

                storyDescription = story.getStoryDescription();

                Glide.with(view)
                        .load(story.getSotryCoverImageUrl())
                        .into(imgCoverStory);
            }
        });
    }

    public Map<String, Object> sortMap(Map<String, Object> data){
        Map<String, Object> sortedMap = data.entrySet()
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