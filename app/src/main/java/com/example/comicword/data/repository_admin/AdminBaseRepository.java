package com.example.comicword.data.repository_admin;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class AdminBaseRepository {

        protected FirebaseFirestore db;
        protected CollectionReference coRef;

        public interface OnDataFetchedListener<T, L> {
            void onDataFetched(T data, L value);
        }
}
