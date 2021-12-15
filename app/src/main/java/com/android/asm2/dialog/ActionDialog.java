package com.android.asm2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;

import com.android.asm2.R;
import com.android.asm2.activity.ReportActivity;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.database.ReportDatabase;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.helper.FileHelper;
import com.android.asm2.helper.NotificationHelper;
import com.android.asm2.model.Report;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class ActionDialog extends Dialog {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActionDialog(Context context, int themeResId, ZoneInfoActivity activity, Zone zone) {
        super(context, themeResId);
        setContentView(R.layout.popup_more_actions);
        setTitle("Choose one action");

        Button editBtn = findViewById(R.id.more_actions_popup_edit_btn);
        Button endBtn = findViewById(R.id.more_actions_popup_end_btn);
        Button volunteerListBtn = findViewById(R.id.more_actions_popup_volunteer_list_btn);
        Button reportBtn = findViewById(R.id.more_actions_popup_report_btn);
        ImageButton closeBtn = findViewById(R.id.more_actions_popup_close_btn);

        ReportDatabase reportDatabase = ReportDatabase.getInstance();
        Report report = reportDatabase.getReportByZoneId(zone.getId());

        if (report != null) reportBtn.setVisibility(View.VISIBLE);
        else if (UserDatabase.getCurrentUser().getRole().equals("leader"))
            endBtn.setVisibility(View.VISIBLE);

        closeBtn.setOnClickListener(v -> dismiss());
        editBtn.setOnClickListener(v -> {
            dismiss();
            activity.changeToEditFrag(zone, false);
        });
        endBtn.setOnClickListener(v -> {
            dismiss();
            Intent intent = new Intent(activity, ReportActivity.class);
            intent.putExtra("isAdded", true);
            intent.putExtra("zoneId", zone.getId());
            intent.putExtra("zoneName", zone.getName());
            activity.startActivity(intent);
        });
        volunteerListBtn.setOnClickListener(v -> {
            dismiss();
        });
        reportBtn.setOnClickListener(v -> {
            dismiss();
            Intent intent = new Intent(activity, ReportActivity.class);
            intent.putExtra("isAdded", false);
            intent.putExtra("zoneId", zone.getId());
            intent.putExtra("zoneName", zone.getName());
            activity.startActivity(intent);
        });
        volunteerListBtn.setOnClickListener(v -> {
            NotificationHelper helper = NotificationHelper.getInstance();
            helper.createNotification("Volunteer list", "Volunteer list for " + zone.getId()
                    + ": " + zone.getName() + " have been stored");
            UserDatabase userDatabase = UserDatabase.getInstance();
            ArrayList<User> userArrayList = userDatabase.getAllUsers();
            for (User user :userArrayList) {
                if (user.isJoinedZone(zone.getId())) {
                    FileHelper.save(user.getName() + "; username: " + user.getUsername() + "; email: " + user.getEmail());
                }
            }
        });
    }
}
