package com.example.comicword.ui.adapter_admin;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicword.R;
import com.example.comicword.data.model.User;

import org.w3c.dom.Text;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.AdminUserViewHolder> {

    private List<User> userList;

    private OnAdminUserClickListener onAdminUserClickListener;

    private OnUserRoleTextChange onUserRoleTextChange;

    private boolean isItemSelectedListenerAttached = false;

    public void setOnUserRoleTextChange(OnUserRoleTextChange onUserRoleTextChange) {
        this.onUserRoleTextChange = onUserRoleTextChange;
    }

    public void setOnAdminUserClickListener(OnAdminUserClickListener onAdminUserClickListener) {
        this.onAdminUserClickListener = onAdminUserClickListener;
    }

    public AdminUserAdapter(List<User> userList) {
        this.userList = userList;
    }
    @NonNull
    @Override
    public AdminUserAdapter.AdminUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_admin, parent, false);

        return new AdminUserAdapter.AdminUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.AdminUserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = userList.get(position);

        holder.txtUserId.setText(user.getUserId());
        holder.txtUserName.setText(user.getUserName());
        holder.txtUserEmail.setText(user.getUserEmail());

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                holder.itemView.getContext(),
                R.array.user_roles, // Tạo mảng string resources cho user roles trong res/values/strings.xml
                android.R.layout.simple_spinner_item
        );



        holder.btnDeletUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdminUserClickListener.onAdminUserCLick(user.getUserId());
            }
        });

        if (!isItemSelectedListenerAttached) {
            holder.spinnerUserRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String selectedUserRole = holder.spinnerUserRole.getSelectedItem().toString();

                    onUserRoleTextChange.onUserRoleText(user.getUserRole(), selectedUserRole, user.getUserId());
                    user.setUserRole(selectedUserRole);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerUserRole.setAdapter(spinnerAdapter);

        int userRolePosition = spinnerAdapter.getPosition(user.getUserRole());
        holder.spinnerUserRole.setSelection(userRolePosition);
        isItemSelectedListenerAttached = true;
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class AdminUserViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDeletUser;
        private TextView txtUserId;
        private TextView txtUserName;
        private TextView txtUserEmail;
        private Spinner spinnerUserRole ;

        public AdminUserViewHolder(View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.txtUserID);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtUserEmail = itemView.findViewById(R.id.txtUserEmail);
            spinnerUserRole = itemView.findViewById(R.id.spinnerUserRole);
            btnDeletUser = itemView.findViewById(R.id.btnDeletUser);
        }
    }

    public interface OnAdminUserClickListener {
        void onAdminUserCLick(String userId);
    }

    public interface OnUserRoleTextChange {
        void onUserRoleText(String oldUserRole, String newUserRole,String userId);
    }

}
