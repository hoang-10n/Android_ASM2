package com.android.asm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.UserDialog;
import com.android.asm2.activity.HomeActivity;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;

public class UserInfoFrag extends Fragment {
    private int hosted;

    public UserInfoFrag(int hosted) {
        this.hosted = hosted;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        TextView usernameTxt = view.findViewById(R.id.user_info_frag_username_txt);
        TextView passwordTxt = view.findViewById(R.id.user_info_frag_password_txt);
        TextView emailTxt = view.findViewById(R.id.user_info_frag_email_txt);
        TextView phoneTxt = view.findViewById(R.id.user_info_frag_phone_txt);
        TextView nameTxt = view.findViewById(R.id.user_info_frag_name_txt);
        TextView roleTxt = view.findViewById(R.id.user_info_frag_role_txt);
        TextView joinedTxt = view.findViewById(R.id.user_info_frag_joined_txt);
        TextView hostedTxt = view.findViewById(R.id.user_info_frag_hosted_txt);
        Button logoutBtn = view.findViewById(R.id.user_info_frag_logout_btn);
        Button changePopupBtn = view.findViewById(R.id.user_info_frag_change_popup_btn);
        UserDialog userDialog = new UserDialog(getContext(), android.R.style.Theme_Dialog,
                (HomeActivity) requireActivity());

        User user = UserDatabase.getCurrentUser();
        usernameTxt.setText(user.getUsername());
        passwordTxt.setText(user.getPassword());
        emailTxt.setText(user.getEmail());
        phoneTxt.setText(user.getPhone());
        nameTxt.setText(user.getName());
        roleTxt.setText(user.getRole());
        joinedTxt.setText(user.getJoinedZones().size() + "");
        hostedTxt.setText(hosted + "");

        logoutBtn.setOnClickListener(v -> requireActivity().finish());
        changePopupBtn.setOnClickListener(v -> userDialog.show());
        return view;
    }
}