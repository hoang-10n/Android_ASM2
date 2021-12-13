package com.android.asm2.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.asm2.activity.AdminActivity;
import com.android.asm2.activity.HomeActivity;
import com.android.asm2.R;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;

public class LoginFrag extends Fragment {
    EditText usernameInput, passwordInput;
    TextView errorTxt;

    public LoginFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameInput = view.findViewById(R.id.login_frag_username_input);
        passwordInput = view.findViewById(R.id.login_frag_password_input);
        errorTxt = view.findViewById(R.id.login_frag_error_txt);
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void enterApp() {
        UserDatabase userDatabase = UserDatabase.getInstance();
        String usernameStr = usernameInput.getText().toString();
        String passwordStr = passwordInput.getText().toString();
        User user = userDatabase.getUserByUsername(usernameStr);
        if (usernameStr.equals("") || passwordStr.equals("")) {
            errorTxt.setText("Fill all fields");
        } else if (usernameStr.equals("ad") && passwordStr.equals("ad")) {
            Intent intent = new Intent(requireContext(), AdminActivity.class);
            UserDatabase.setCurrentUser(new User("ad"));
            requireActivity().startActivity(intent);
        } else if (user == null) {
            errorTxt.setText("Account does not exist");
        } else if (!user.getPassword().equals(passwordStr)) {
            errorTxt.setText("Wrong password");
        } else {
            errorTxt.setText("");
            Intent intent = new Intent(requireContext(), HomeActivity.class);
            UserDatabase.setCurrentUser(user);
            requireActivity().startActivity(intent);
        }
    }
}