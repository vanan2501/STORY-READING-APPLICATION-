package com.example.comicword.data.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public abstract class BaseRepository {

    protected FirebaseFirestore db;
    protected CollectionReference coRef;
    public interface OnDataFetchedListener<T, L> {
        void onDataFetched(T data, L value);
    }
}
