package com.android.asm2;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.asm2.activity.HomeActivity;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;
import com.google.gson.Gson;

public class UserDialog extends Dialog {
    private User user;
    private EditText passwordInput, emailInput, phoneInput, nameInput;
    private RadioButton volunteer, leader;

    public UserDialog(Context context, int themeResId, HomeActivity activity, User user) {
        super(context, themeResId);
        setContentView(R.layout.popup_user_change);
        getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent);
        setTitle("Change user info");
        this.user = user;

        passwordInput = findViewById(R.id.user_change_popup_password_input);
        emailInput = findViewById(R.id.user_change_popup_email_input);
        phoneInput = findViewById(R.id.user_change_popup_phone_input);
        nameInput = findViewById(R.id.user_change_popup_name_input);
        volunteer = findViewById(R.id.user_change_popup_volunteer_radio);
        leader = findViewById(R.id.user_change_popup_leader_radio);
        Button saveBtn = findViewById(R.id.user_change_popup_save_btn);
        Button discardBtn = findViewById(R.id.user_change_popup_discard_btn);

        resetData();
        discardBtn.setOnClickListener(v -> {
            resetData();
            dismiss();
        });
        saveBtn.setOnClickListener(v -> {
            user.setPassword(passwordInput.getText().toString());
            user.setEmail(emailInput.getText().toString().trim());
            user.setPhone(phoneInput.getText().toString());
            user.setName(nameInput.getText().toString().trim());
            user.setRole(leader.isChecked() ? "leader" : "volunteer");
            dismiss();
            activity.editUser(user);
        });
    }

    private void resetData() {
        passwordInput.setText(user.getPassword());
        emailInput.setText(user.getEmail());
        phoneInput.setText(user.getPhone());
        nameInput.setText(user.getName());
        if (user.getRole().equals("leader")) leader.setChecked(true);
        else volunteer.setChecked(true);
    }
}
