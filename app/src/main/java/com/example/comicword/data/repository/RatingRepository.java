package com.example.comicword.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comicword.data.model.Rating;
import com.example.comicword.ui.activity.DetailStoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class RatingRepository extends  BaseRepository {

    private final static String TAG = "RATING_STORY_HISTORY";
    public RatingRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("rating");
    }

    public void addRatingStory(Rating rating, String userId){

        Query query = coRef.whereEqualTo("storyId", rating.getStoryId())
                        .whereEqualTo("userId", userId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot == null || querySnapshot.isEmpty()) {
                        coRef.add(rating);
                    } else {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                        String ratingId = documentSnapshot.getId();

                        coRef.document(ratingId).update("value", rating.getValue());
                    }
                } else {
                    Log.e(TAG, "Error checking history existence.", task.getException());
                }
            }
        });
    }

    public void getRatingStory(String storyId, String userId, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("storyId", storyId)
                        .whereEqualTo("userId", userId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot.getDocuments().equals(null) || !querySnapshot.isEmpty()) {

                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                        Rating rating = documentSnapshot.toObject(Rating.class);

                        if(documentSnapshot != null){

                            Log.i(TAG, "" + rating.getValue() );
                            listener.onDataFetched(rating.getValue(), 0);
                        }
                    } else {
                        listener.onDataFetched( 0, 0);
                    }
                } else {
                    Log.e(TAG, "Error checking history existence.", task.getException());
                }
            }
        });
    }

}
