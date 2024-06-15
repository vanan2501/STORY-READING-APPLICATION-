package com.example.comicword.ui.fragment_admin;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.comicword.R;
import com.example.comicword.data.model.Category;
import com.example.comicword.data.repository_admin.AdminBaseRepository;
import com.example.comicword.data.repository_admin.AdminCategoryRepository;

import java.util.HashMap;


public class OverlayDialogFragment extends DialogFragment {

    private CategoryAdminFragment categoryAdminFragment;
    public OverlayDialogFragment() {
        // Required empty public constructor
    }

    public static OverlayDialogFragment newInstance(String param1, String param2) {
        OverlayDialogFragment fragment = new OverlayDialogFragment();
        Bundle args = new Bundle();

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
        View rootView = inflater.inflate(R.layout.fragment_overlay_dialog, container, false);

        Button addButton = rootView.findViewById(R.id.buttonAddCategory);
        EditText categoryNameEditText = rootView.findViewById(R.id.editTextCategoryName);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện thêm danh mục ở đây
                String categoryName = categoryNameEditText.getText().toString();
                // Gọi hàm để thêm danh mục

                Category category = new Category(normalizeString(categoryName), false);
                OnCategoryAddedListener listener = (OnCategoryAddedListener) getParentFragment();
                if (listener != null) {
                    listener.onCategoryAdded(category);
                }

                dismiss(); // Đóng lớp phủ sau khi xử lý xong
            }
        });

        return rootView;
    }

    public static String normalizeString(String input) {
        // Loại bỏ khoảng trắng đầu và cuối chuỗi
        String trimmedInput = input.trim();

        // Viết hoa chữ đầu và chuyển các chữ cái còn lại thành chữ thường
        String normalized = trimmedInput.substring(0, 1).toUpperCase() + trimmedInput.substring(1).toLowerCase();

        return normalized;
    }

    public interface OnCategoryAddedListener {
        void onCategoryAdded(Category category);
    }
}