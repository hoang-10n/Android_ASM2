package com.android.asm2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.asm2.activity.HomeActivity;
import com.android.asm2.R;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;


public class RegisterFrag extends Fragment {
    EditText usernameInput, passwordInput, emailInput, phoneInput, confirmInput, nameInput;
    TextView errorTxt;
    boolean isVolunteer = true;

    public RegisterFrag() {
        // Required empty public constructor
    }

//    public static RegisterFrag newInstance(String param1, String param2) {
//        RegisterFrag fragment = new RegisterFrag();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        usernameInput = view.findViewById(R.id.register_frag_username_input);
        passwordInput = view.findViewById(R.id.register_frag_password_input);
        emailInput = view.findViewById(R.id.register_frag_email_input);
        phoneInput = view.findViewById(R.id.register_frag_phone_input);
        confirmInput = view.findViewById(R.id.register_frag_confirm_input);
        nameInput = view.findViewById(R.id.register_frag_name_input);
        errorTxt = view.findViewById(R.id.register_frag_error_txt);
        RadioButton volunteerRadio = view.findViewById(R.id.register_frag_volunteer_radio);
        RadioButton leaderRadio = view.findViewById(R.id.register_frag_leader_radio);

        volunteerRadio.setOnClickListener(v -> isVolunteer = true);
        leaderRadio.setOnClickListener(v -> isVolunteer = false);
        return view;
    }

    public void enterApp() {
        UserDatabase userDatabase = new UserDatabase(getContext());
        String usernameStr = usernameInput.getText().toString();
        String passwordStr = passwordInput.getText().toString();
        String emailStr = emailInput.getText().toString();
        String phoneStr = phoneInput.getText().toString();
        String confirmStr = confirmInput.getText().toString();
        String nameStr = nameInput.getText().toString();
        if (usernameStr.equals("") || passwordStr.equals("") || emailStr.equals("") ||
                phoneStr.equals("") || nameStr.equals("")) {
            errorTxt.setText("Fill all fields");
        } else if (!confirmStr.equals(passwordStr)) {
            errorTxt.setText("Confirmed password doesn't match");
        } else if (userDatabase.getUserByUsername(usernameStr) != null) {
            errorTxt.setText("Account already existed");
        } else if (userDatabase.getUserByEmail(emailStr) != null) {
            errorTxt.setText("Email already used");
        } else {
            errorTxt.setText("");
            User user = new User(usernameStr, passwordStr, emailStr, phoneStr, nameStr, isVolunteer ? "volunteer" : "leader");
//            User user = new User(usernameStr, passwordStr, emailStr, phoneStr, nameStr, "admin");
            userDatabase.addUser(user);
            Intent intent = new Intent(requireContext(), HomeActivity.class);
            intent.putExtra("username", usernameStr);
            requireActivity().startActivity(intent);
        }
    }
}