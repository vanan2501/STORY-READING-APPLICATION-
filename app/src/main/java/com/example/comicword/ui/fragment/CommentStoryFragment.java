package com.example.comicword.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicword.R;
import com.example.comicword.data.repository.BaseRepository;
import com.example.comicword.data.repository.CommentRepository;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.example.comicword.ui.adapter.CommentAdapter;

import com.example.comicword.data.model.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommentStoryFragment extends Fragment {

    private static final String param1 = "storyId";

    private static final String param2 = "storyTitle";

    // TODO: Rename and change types of parameters
    private String storyId;
    private String storyTitle;
    private CommentRepository commentRepository;
    private DetailStoryActivity detailStoryActivity;
    public CommentStoryFragment() {
        // Required empty public constructor
    }



    public static CommentStoryFragment newInstance(String storyId, String storyTitle) {
        CommentStoryFragment fragment = new CommentStoryFragment();
        Bundle args = new Bundle();
        args.putString(param1, storyId);
        args.putString(param2, storyTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storyId = getArguments().getString(param1);
            storyTitle = getArguments().getString(param2);
        }

        commentRepository = new CommentRepository();
        detailStoryActivity = new DetailStoryActivity();
    }

    private RecyclerView commentRecyclerView;
    private EditText commentEditText;
    private Button postCommentButton;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private TextView titleCommentStory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_story, container, false);

        commentRecyclerView = view.findViewById(R.id.commentRecyclerView);
        commentEditText = view.findViewById(R.id.commentEditText);
        postCommentButton = view.findViewById(R.id.postCommentButton);
        titleCommentStory = view.findViewById(R.id.titleCommentStory);

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentRecyclerView.setAdapter(commentAdapter);

        titleCommentStory.setText(storyTitle);


        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEditText.getText().toString();


                String authorName; // Replace with the actual author's name


                FirebaseUser user = detailStoryActivity.user;

                if( user.getDisplayName().equals("")){
                    authorName = user.getEmail().substring( 0 ,user.getEmail().indexOf("@"));
                } else {
                    authorName = user.getDisplayName();
                }

                Comment newComment = new Comment(commentText, storyId ,authorName, storyId, commentList.size());
                commentList.add(newComment);
                commentAdapter.notifyItemInserted(commentList.size() - 1);
                commentEditText.setText("");

                commentRecyclerView.smoothScrollToPosition(commentList.size() - 1);
                commentRepository.addCommentStory(newComment);
            }
        });

        // Simulate loading existing comments from a data source (e.g., Firebase)
        simulateLoadingComments();

        return view;
    }

    // Simulate loading existing comments
    private void simulateLoadingComments() {
        // Replace this with actual data retrieval logic

        commentRepository.getComment(storyId, new BaseRepository.OnDataFetchedListener<List<Comment>, String>() {
            @Override
            public void onDataFetched(List<Comment> comments, String storyId) {

                for (Comment comment :  sortCommentListByIndex(comments)) {

                    commentList.add(comment);

                }

                commentAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Comment> sortCommentListByIndex(List<Comment> comments) {

        return comments.stream()
                .sorted(Comparator.comparingInt(comment -> comment.getIndexComment()))
                .collect(Collectors.toList());
    }
}