package com.example.comicword.data.repository_admin;

import com.example.comicword.data.model.Story;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdminStoryRepository extends AdminBaseRepository{

    public AdminStoryRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.coRef = db.collection("story");
    }

    public void getListSotry(OnDataFetchedListener listener){

        coRef.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot.getDocuments() != null || !querySnapshot.isEmpty()){

                            Map<String, Story> storyMap = new HashMap<>();
                            Story story = new Story();
                            String storyId;
                            for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                story = documentSnapshot.toObject(Story.class);
                                storyId = documentSnapshot.getId();

                                storyMap.put(storyId, story);
                            }

                            if(storyMap!= null) {
                                listener.onDataFetched(storyMap, true);
                            }
                        }
                    }
                });

    }
}
