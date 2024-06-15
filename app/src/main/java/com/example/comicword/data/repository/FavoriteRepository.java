package com.example.comicword.data.repository;

import android.util.Log;

import com.example.comicword.data.model.Favorite;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRepository extends BaseRepository{

    public FavoriteRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("favorite");
    }

    public void addFavorite(Favorite favorite){
        Query query = coRef.whereEqualTo("storyId" , favorite.getStoryId())
                .whereEqualTo("userId", favorite.getUserId());

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();

                        if( querySnapshot.getDocuments() == null || querySnapshot.isEmpty()) {
                            coRef.add(favorite);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FAVORITE_REPOSITORY_TAG", "" + e.getMessage());
                });
    }

    public void deleteFavorite(String favoriteId) {
        coRef.document(favoriteId).delete();
    }

    public void getListFavorite(String userId, OnDataFetchedListener listener){

        Query query = coRef.whereEqualTo("userId", userId);


        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            List<String> storyIds = new ArrayList<>();
                            List<String> favoriteIds = new ArrayList<>();

                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()){
                                String favoriteId = documentSnapshot.getId();

                                Favorite nFavorite = documentSnapshot.toObject(Favorite.class);

                                storyIds.add(nFavorite.getStoryId());
                                favoriteIds.add(favoriteId);
                            }

                            if(storyIds != null) {
                                listener.onDataFetched(storyIds, favoriteIds);
                            }
                        }
                    } else {
                        listener.onDataFetched(null, null);
                    }
                });
    }


}
