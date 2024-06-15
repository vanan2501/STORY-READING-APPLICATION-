package com.example.comicword.data.repository;

import android.util.Log;

import com.example.comicword.data.model.Comment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CommentRepository extends BaseRepository {

    private final static String TAG = "COMMENT_REPOSITORY_TAG";
    public CommentRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("comment");
    }

    public void addCommentStory(Comment comment){

        coRef.add(comment)
                .addOnSuccessListener(documentReference -> {
                    Log.i(TAG, "Add comment success");
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "" + e.getMessage());
                });
    }

    public void getComment(String storyId, OnDataFetchedListener listener){

        Query query = coRef.whereEqualTo("storyId", storyId);

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if(queryDocumentSnapshots.getDocuments() != null || !queryDocumentSnapshots.isEmpty()) {

                        List<Comment> commentList = new ArrayList<>();

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            commentList.add(documentSnapshot.toObject(Comment.class));
                        }

                        if(commentList != null) {
                            listener.onDataFetched(commentList, storyId);
                        } else {

                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "" +e.getMessage());
                });
    }
}
