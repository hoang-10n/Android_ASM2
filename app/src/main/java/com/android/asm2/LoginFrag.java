package com.android.asm2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFrag extends Fragment {
    EditText usernameInput, passwordInput;
    TextView errorTxt;

    public LoginFrag() {
        // Required empty public constructor
    }

//    public static LoginFrag newInstance() {
//        LoginFrag fragment = new LoginFrag();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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

    public void enterApp() {
        String usernameStr = usernameInput.getText().toString();
        String passwordStr = passwordInput.getText().toString();
        if (usernameStr.equals("") || passwordStr.equals("")) {
            errorTxt.setText("Fill all fields");
        } else if (!usernameStr.equals("admin") && !passwordStr.equals("admin")) {
            errorTxt.setText("Account does not exist");
        } else {
            errorTxt.setText("");
        }
        Toast.makeText(requireContext(), "Hello login", Toast.LENGTH_SHORT).show();
    }
}