package com.example.comicword.data.repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comicword.R;
import com.example.comicword.data.model.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StoryRepository extends BaseRepository{

    public StoryRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("story");
    }


    public void getStories(OnDataFetchedListener listener) {
        coRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                    List<Story> stories = new ArrayList<>();
                    List<String> listId = new ArrayList<>();

                    for (DocumentSnapshot document : documents) {
                        // Convert DocumentSnapshot to Story object
                        Story story = document.toObject(Story.class);
                        String storyId = document.getId();
                        if (story != null && storyId != null) {
                            stories.add(story);
                            listId.add(storyId);
                        }
                    }

                    listener.onDataFetched(stories, listId);
                } else {

                }
            } else {

            }
        });
    }

    public void getStoryById(String storyId, OnDataFetchedListener listener){
        coRef.document(storyId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        Story story = documentSnapshot.toObject(Story.class);

                        listener.onDataFetched(story, storyId);
                    } else {
                        Log.e("STORY_REPOSITORY", "Error");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("STORY_REPOSITORY", "Failed to get sotry : " + e.getMessage());
                });
    }

    public void getListStoryWhereIn(List<String> storyIds, OnDataFetchedListener listener){

        coRef.whereIn(FieldPath.documentId(), storyIds)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()){

                            List<Story> storiList = new ArrayList<>();
                            List<String> storyListIds = new ArrayList<>();

                            Story story;
                            for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()){

                                story = documentSnapshot.toObject(Story.class);
                                storyListIds.add(documentSnapshot.getId());
                                storiList.add(story);

                            }

                            listener.onDataFetched(storiList, storyListIds);
                        }
                    }
                });
    }

    public void getListStoryOfCategory(String categoryId, OnDataFetchedListener listener){
        Query query = coRef.whereEqualTo("categoryId", categoryId);

        query.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()) {
                            List<Story> storyList = new ArrayList<>();
                            List<String> storyIdList = new ArrayList<>();

                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {

                                storyList.add(documentSnapshot.toObject(Story.class));
                                storyIdList.add(documentSnapshot.getId());
                            }

                            if(storyList != null && storyIdList != null) {
                                listener.onDataFetched(storyList, storyIdList);
                            }
                        }

                    }
                });

    }

}
