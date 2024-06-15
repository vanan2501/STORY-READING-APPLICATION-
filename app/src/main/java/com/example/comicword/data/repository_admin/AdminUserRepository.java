package com.example.comicword.data.repository_admin;

import android.util.Log;

import com.example.comicword.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminUserRepository extends AdminBaseRepository{

    public AdminUserRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("user");
    }

    public void getListUser(String userId, OnDataFetchedListener listener) {

        Query query = coRef.whereNotEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            List<User> userList =  new ArrayList<>();

                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {

                                userList.add(documentSnapshot.toObject(User.class));

                            }

                            if(userList != null) {
                                listener.onDataFetched(userList, true);
                            } else {
                                listener.onDataFetched(null, false);
                            }
                        }
                    }

                });
    }

    public boolean updateUserRole(String userId, String newUserRole) {

        AtomicBoolean isSuccess = new AtomicBoolean(true);

        Query query = coRef.whereEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                            if(documentSnapshot != null) {
                                String documentId = documentSnapshot.getId();

                                if(documentId != null) {

                                    coRef.document(documentId).update("userRole", newUserRole);
                                     isSuccess.set(true);
                                }
                            } else {
                                Log.i("ADMIN_USER_REPOSITORY", documentSnapshot.toString());
                                isSuccess.set(false);
                                Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + documentSnapshot.toString());
                            }
                        } else {
                            Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + querySnapshot.toString());
                        }
                    } else {
                        Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + task.getException());
                    }
                });

        return isSuccess.get();
    }

    public boolean deleteUser(String userId) {

        AtomicBoolean isSuccess = new AtomicBoolean(true);
        Query query = coRef.whereEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

                            if(documentSnapshot != null) {
                                String documentId = documentSnapshot.getId();

                                if(documentId != null) {

                                    coRef.document(documentId).delete();

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    user.delete();
                                    isSuccess.set(true);
                                }
                            } else {
                                Log.i("ADMIN_USER_REPOSITORY", documentSnapshot.toString());
                                isSuccess.set(false);
                                Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + documentSnapshot.toString());
                            }
                        } else {
                            Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + querySnapshot.toString());
                        }
                    } else {
                        Log.e("ADMIN_USER_REPOSITORY", "errrorr : " + task.getException());
                    }
                });
        return isSuccess.get();

    }

}
