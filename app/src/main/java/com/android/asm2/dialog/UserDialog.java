package com.android.asm2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.asm2.R;
import com.android.asm2.activity.HomeActivity;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;
import com.google.gson.Gson;

/***
 * User Dialog is used in User Info Fragment to change user's information
 */
public class UserDialog extends Dialog {
    private final EditText passwordInput, emailInput, phoneInput, nameInput;
    private final RadioButton volunteer, leader;
    private final User user;

    public UserDialog(Context context, int themeResId, HomeActivity activity) {
        super(context, themeResId);
        setContentView(R.layout.popup_user_change);
        getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent);
        setTitle("Change user info");
        user = UserDatabase.getCurrentUser();

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
            activity.editUser();
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
