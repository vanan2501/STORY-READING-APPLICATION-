package com.example.comicword.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.comicword.data.model.Favorite;
import com.example.comicword.data.model.History;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryRepository extends BaseRepository{
    private final static String TAG = "HISTORY_TAG";


    public HistoryRepository()
    {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("history");
    }

    public void addHistoryStory(History history) {

        Query query = coRef.whereEqualTo("storyId", history.getStoryId());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot == null || querySnapshot.isEmpty()) {
                        coRef.add(history);
                    } else {
                        Log.i(TAG, "History already exists for this story.");
                    }
                } else {
                    Log.e(TAG, "Error checking history existence.", task.getException());
                }
            }
        });
    }

    public void getListHistory(String userId, OnDataFetchedListener listener){

        Query query = coRef.whereEqualTo("userId", userId);


        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            List<String> storyIds = new ArrayList<>();
                            List<String> historyDateTamps = new ArrayList<>();

                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()){
                                History nHistory = documentSnapshot.toObject(History.class);

                                storyIds.add(nHistory.getStoryId());
                                historyDateTamps.add(nHistory.getHistoryTimeTamp());
                            }

                            if(storyIds != null) {
                                listener.onDataFetched(storyIds, historyDateTamps);
                            } else
                            {
                                listener.onDataFetched(null, null);
                            }
                        }
                    }
                });
    }

}
