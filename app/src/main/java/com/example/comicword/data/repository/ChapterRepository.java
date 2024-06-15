package com.example.comicword.data.repository;

import android.util.Log;

//import com.example.comicword.data.model.Chapter;
//import com.example.comicword.data.model.Story;
import androidx.annotation.NonNull;

import com.example.comicword.data.model.Chapter;
import com.example.comicword.data.model.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChapterRepository extends BaseRepository {

    private final static String TAG = "CHAPTER_TAG";
    public ChapterRepository()
    {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("chapter");
    }

    public void getChaptersByStoryId(OnDataFetchedListener listener, String storyId){

        Query query = coRef.whereEqualTo("storyId", storyId);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null && !querySnapshot.isEmpty()){
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                String id = document.getId();
                                Map<String, Object> chapterContent = (Map<String, Object>) document.getData().get("chapterContent");

                                listener.onDataFetched(chapterContent, id);
                            }
                        }
                        else {

                        }
                    }
                });
    }

    public void getChapterContentUrl(OnDataFetchedListener listener, String chapterId, String chapterNumber) {

        coRef.document(chapterId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> chapterData = documentSnapshot.getData();
                        if (chapterData != null && chapterData.containsKey("chapterContent")) {
                            Map<String, Object> chapterContentMap = (Map<String, Object>) chapterData.get("chapterContent");
                            String desiredKey = chapterNumber;
                            if (chapterContentMap != null && chapterContentMap.containsKey(desiredKey)) {
                                Object desiredValue = chapterContentMap.get(desiredKey);
                                listener.onDataFetched(desiredValue, chapterNumber);
                                // Do something with the desiredValue
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "" + e.getMessage());
                });
    }

    public void getAllChapter(String storyId, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("storyId", storyId);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null && !querySnapshot.isEmpty()){
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                                String id = document.getId();
                                Map<String, Object> chapterContent =  ( Map<String, Object>) document.getData().get("chapterContent");

                                List<String> chapterNumber = new ArrayList<>(chapterContent.keySet());

                                Log.i(TAG, ""+ chapterNumber.toString());
                                listener.onDataFetched(chapterNumber, id);
                            }
                        }
                        else {

                        }
                    }
                });
    }
}
