package com.example.comicword.data.repository;

import android.util.Log;

import com.example.comicword.data.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository extends BaseRepository {
    private static final String TAG = "UserRepository";

    public UserRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("user");
    }



    public void addUser(User user, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("userId", user.getUserId());

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() == null || querySnapshot.isEmpty()) {
                            coRef.add(user);
                            listener.onDataFetched(user, false);
                        } else
                        {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            listener.onDataFetched(documentSnapshot.toObject(User.class), true);
                        }
                    }
                });
    }


    public void checkRole(String userId, OnDataFetchedListener listener) {

        Query query = coRef.whereEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        QuerySnapshot querySnapshot = task.getResult();
                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            String userRole;

                            userRole = querySnapshot.getDocuments().get(0).getString("userRole");

                            if(userRole != null) {
                                listener.onDataFetched(userRole, true);
                            } else {
                                listener.onDataFetched(null, false);
                            }

                        }

                    }
                });
    }
}
