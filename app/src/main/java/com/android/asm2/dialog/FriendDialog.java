package com.android.asm2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.controller.UserController;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

/***
 * Friend Dialog is used in Zone Info Fragment to register for a friend
 */
public class FriendDialog extends Dialog {
    public FriendDialog(Context context, int themeResId, Zone zone) {
        super(context, themeResId);
        setContentView(R.layout.popup_friend_invite);
        getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent);
        setTitle("Invite friend to this zone");

        TextView errorTxt = findViewById(R.id.friend_invite_popup_error_txt);
        EditText emailInput = findViewById(R.id.friend_invite_popup_email_input);
        Button sendBtn = findViewById(R.id.friend_invite_popup_send_btn);
        Button discardBtn = findViewById(R.id.friend_invite_popup_discard_btn);

        discardBtn.setOnClickListener(v -> {
            emailInput.setText("");
            dismiss();
        });

        sendBtn.setOnClickListener(v -> {
            UserDatabase userDatabase = UserDatabase.getInstance();
            User user = userDatabase.getUserByEmail(emailInput.getText().toString().trim());
            if (user != null) {
                if (user.getJoinedZones().contains(zone.getId()) ||
                        zone.getLeader().equals(user.getUsername())) {
                    errorTxt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    user.joinZone(zone.getId());
                    UserController.updateUser(user);
                }
            }
            errorTxt.setVisibility(View.GONE);
            Log.d("TAG", "Invite sent to : " + emailInput.getText().toString());
        });
    }
}
