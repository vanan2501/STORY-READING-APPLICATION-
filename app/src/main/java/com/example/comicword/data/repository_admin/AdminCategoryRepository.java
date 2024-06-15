package com.example.comicword.data.repository_admin;


import android.util.Log;

import com.example.comicword.data.model.Category;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdminCategoryRepository extends AdminBaseRepository{

    private final static String TAG = "CATEGORY_ADMIN_REPOSITORY_TAG";

    public AdminCategoryRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("category");
    }

    public void getListCategory(OnDataFetchedListener listener){
        coRef.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {

                            Map<String, Category> categoryMap = new HashMap<>();
                            Category category;
                            String categoryId;

                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                category = documentSnapshot.toObject(Category.class);
                                categoryId = documentSnapshot.getId();

                                categoryMap.put(categoryId, category);
                            }

                            if(categoryMap!= null) {
                                listener.onDataFetched(categoryMap, true);
                            } else {
                                Log.i(TAG, "CategoryMap null.!!");
                            }
                        } else {
                            Log.i(TAG, "Querysnapshot null.!!");
                        }
                    }
                });
    }

    public void updateIsDelete(String categoryId, Boolean value) {

        coRef.document(categoryId)
                .update("isDelete", value);
    }

    public void addCategory(Category category, OnDataFetchedListener listener) {

        coRef.add(category)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        String documentId = task.getResult().getId();

                        coRef.document(documentId).get().addOnCompleteListener(documentTask -> {
                            if (documentTask.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = documentTask.getResult();

                                if (documentSnapshot.exists()) {

                                    Category newCategory =documentSnapshot.toObject(Category.class);

                                    listener.onDataFetched(newCategory, documentSnapshot.getId());
                                } else {
                                    Log.i(TAG, "Loi khi them category.!!");
                                }
                            } else {
                                    Log.i(TAG, "Loi khi documentTask.!!");
                            }
                        });
                    }
                });

    }
}
