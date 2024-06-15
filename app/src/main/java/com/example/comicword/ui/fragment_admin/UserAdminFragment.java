package com.example.comicword.ui.fragment_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comicword.R;
import com.example.comicword.data.model.User;
import com.example.comicword.data.repository_admin.AdminBaseRepository;
import com.example.comicword.data.repository_admin.AdminUserRepository;
import com.example.comicword.ui.adapter_admin.AdminUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdminFragment extends Fragment {

    private AdminUserRepository adminUserRepository;
    private AdminUserAdapter adminUserAdapter;
    private RecyclerView recyclerView;
    private List<User> newUserList;
    public UserAdminFragment() {
        // Required empty public constructor
    }

    private final static String param1 = "userId";
    private String userId;

    public static UserAdminFragment newInstance(String userId) {
        UserAdminFragment fragment = new UserAdminFragment();
        Bundle bundle = new Bundle();
        bundle.putString(param1, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            userId = getArguments().getString(param1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_admin, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewContainerAdmin);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        newUserList = new ArrayList<>();

        adminUserAdapter = new AdminUserAdapter(newUserList);

        recyclerView.setAdapter(adminUserAdapter);

        loadData(view);

        return view;
    }


    private void loadData(View view) {
        adminUserRepository = new AdminUserRepository();

        Log.i("ADMIN_USER", "" + userId);
        adminUserRepository.getListUser(userId ,new AdminBaseRepository.OnDataFetchedListener<List<User>, Boolean>() {
            @Override
            public void onDataFetched(List<User> userList, Boolean value) {
                Log.i("ADMIN_USER_FRAGMENT", "" + userList.toString());
                userList.forEach(user -> {
                    newUserList.add(user);
                });
                adminUserAdapter.notifyDataSetChanged();

                adminUserAdapter.setOnAdminUserClickListener(new AdminUserAdapter.OnAdminUserClickListener() {
                    @Override
                    public void onAdminUserCLick(String nUserId) {

                        if(adminUserRepository.deleteUser(nUserId)) {
                            Toast.makeText(view.getContext(), "Bạn đã xóa thành công.!", Toast.LENGTH_SHORT).show();
                            int indexUser = -1;

                            for(int i = 0; i < newUserList.size(); i++) {
                                if(newUserList.get(i).getUserId().equals(nUserId)) {
                                    indexUser = i;
                                }
                            }

                            if(indexUser != -1) {
                                newUserList.remove(indexUser);
                                adminUserAdapter.notifyDataSetChanged();
                            }

                        }
                        Toast.makeText(view.getContext(), "" + userId, Toast.LENGTH_SHORT).show();
                    }
                });

                adminUserAdapter.setOnUserRoleTextChange(new AdminUserAdapter.OnUserRoleTextChange() {
                    @Override
                    public void onUserRoleText(String oldUserRole, String newUserRole,String nUserId) {

                        if(!adminUserRepository.updateUserRole(nUserId, newUserRole)) {
                            Toast.makeText(view.getContext(), "Bạn thay đổi vai trò thất bại! Vui lòng thử lại.!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }


}