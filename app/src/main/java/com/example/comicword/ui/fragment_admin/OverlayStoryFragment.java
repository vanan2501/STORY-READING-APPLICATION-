package com.example.comicword.ui.fragment_admin;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.comicword.R;
import com.example.comicword.data.model.Story;


public class OverlayStoryFragment extends DialogFragment {


    public OverlayStoryFragment() {
        // Required empty public constructor
    }

    public static OverlayStoryFragment newInstance(String param1, String param2) {
        OverlayStoryFragment fragment = new OverlayStoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_overlay_story, container, false);

        setActionAddStory(view);

        return view;
    }
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImagUri;
    private  Button buttonAddStory,btnUploadFileImage;
    private ImageView imageStory;
    private EditText etTitleStory, etAuthorStory,
            etDescriptionStory;
    private Spinner spinerCategory, spinerTypeStory;

    public void setActionAddStory(View view){
        buttonAddStory = view.findViewById(R.id.buttonAddStory);
        btnUploadFileImage = view.findViewById(R.id.btnUploadFileImage);
        imageStory = view.findViewById(R.id.imageStory);
        etTitleStory = view.findViewById(R.id.etTitleStory);
        etAuthorStory = view.findViewById(R.id.etAuthorStory);
        etDescriptionStory = view.findViewById(R.id.etDescriptionStory);
        spinerTypeStory = view.findViewById(R.id.spinerTypeStory);
        spinerCategory = view.findViewById(R.id.spinerCategory);

        buttonAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Story story = new Story();




                dismiss();
            }
        });

        btnUploadFileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
                imageStory.setVisibility(View.VISIBLE);
                btnUploadFileImage.setVisibility(View.GONE);
            }
        });
    }

    public void handleAddStory() {

    }

    public void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null) {
             mImagUri = data.getData();

            Glide.with(getActivity())
                    .load(mImagUri)
                    .into(imageStory);
        }
    }
}