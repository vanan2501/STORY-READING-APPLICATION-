package com.example.comicword.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.comicword.data.model.Bookmark;
import com.example.comicword.data.model.Rating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookmarkReposiitory extends BaseRepository {
    private final static String TAG = "BOOK_MARK_REPOSITORY_TAG";

    public BookmarkReposiitory(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("bookmark");
    }

    public void addBookmarkStory(Bookmark newBookmark) {

        Query query = coRef.whereEqualTo("storyId", newBookmark.getStoryId())
                .whereEqualTo("userId", newBookmark.getUserId());

        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot == null || querySnapshot.isEmpty()) {
                        coRef.add(newBookmark);
                    } else {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        String bookmarkId = documentSnapshot.getId();

                        Bookmark bookmark = documentSnapshot.toObject(Bookmark.class);

                        if (bookmark != null) {
                            List<String> chapterNumbers = bookmark.getChapterNumbers();

                            String chapterNumber = newBookmark.getChapterNumbers().get(0);

                            List<String> updatedChapterNumbers = new ArrayList<>(chapterNumbers);

                            boolean chapterNumberExists = false;
                            for (String s : chapterNumbers) {
                                if (s.equals(chapterNumber)) {
                                    chapterNumberExists = true;
                                    break;
                                }
                            }

                            if (!chapterNumberExists) {
                                updatedChapterNumbers.add(chapterNumber);
                                coRef.document(bookmarkId).update("chapterNumbers", updatedChapterNumbers);
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "Error checking history existence.", task.getException());
                }
            }
        });
    }

    public void getBookmark(String userId, String storyId, OnDataFetchedListener listener) {

        Query query = coRef.whereEqualTo("storyId", storyId)
                .whereEqualTo("userId", userId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                        Bookmark bookmark = documentSnapshot.toObject(Bookmark.class);

                        if (bookmark != null) {
                            List<String> chapterNumbers = bookmark.getChapterNumbers();

                            if(chapterNumbers != null) {
                                listener.onDataFetched(chapterNumbers, 1);
                            }
                        }
                    } else {
                        listener.onDataFetched(null, 0);
                    }
                } else {
                    Log.e(TAG, "Error checking history existence.", task.getException());
                }
            }
        });
    }
}
