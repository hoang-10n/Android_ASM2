package com.android.asm2;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.database.ReportDatabase;
import com.android.asm2.model.Report;
import com.android.asm2.model.Zone;

public class ActionDialog extends Dialog {
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
        else endBtn.setVisibility(View.VISIBLE);

        closeBtn.setOnClickListener(v -> dismiss());
        editBtn.setOnClickListener(v -> {
            dismiss();
            activity.changeToEditFrag(zone, false);
        });
        endBtn.setOnClickListener(v -> {
            dismiss();
        });
        volunteerListBtn.setOnClickListener(v -> {
            dismiss();
        });
        reportBtn.setOnClickListener(v -> {
            dismiss();
        });
    }
}
