package com.example.comicword.data.repository;

import android.util.Log;

import com.example.comicword.data.model.Category;
import com.example.comicword.data.model.Story;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends BaseRepository{
    private final static String TAG = "CATEGORY_TAG";
    public CategoryRepository()
    {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("category");
    }

    public void getTitleCategory(String categoryId, OnDataFetchedListener listener){

        coRef.document(categoryId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                    {
                        Category category = documentSnapshot.toObject(Category.class);

                        //
                        if(category != null) {
                            Log.i(TAG, "" + category.getCategoryTitle());

                            listener.onDataFetched(category, categoryId);
                        } else {
                            Log.i(TAG, "Failed to get category");
                        }
                    }
                });

    }

    public void getListCategory(OnDataFetchedListener listener){

        coRef.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()){

                            List<Category> categoryList = new ArrayList<>();
                            List<String> categoryIdList = new ArrayList<>();
                            Category category;

                            for(DocumentSnapshot document : querySnapshot.getDocuments()) {

                                category = document.toObject(Category.class);

                                categoryList.add(category);
                                categoryIdList.add(document.getId());

                            }

                            listener.onDataFetched(categoryList, categoryIdList);

                        }
                    }
                });
    }
}
