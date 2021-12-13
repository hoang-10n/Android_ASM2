package com.android.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.android.asm2.R;
import com.android.asm2.database.ReportDatabase;
import com.android.asm2.fragment.ReportEditFrag;
import com.android.asm2.fragment.ReportInfoFrag;
import com.android.asm2.model.Report;

public class ReportActivity extends AppCompatActivity {
    private Fragment startFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ImageButton closeBtn = findViewById(R.id.report_activity_close_btn);

        Intent intent = getIntent();
        boolean isAdded = (boolean) intent.getExtras().get("isAdded");
        String zoneId = (String) intent.getExtras().get("zoneId");
        String zoneName = (String) intent.getExtras().get("zoneName");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isAdded) {
            Report newReport = new Report(zoneId);
            startFrag = new ReportEditFrag(newReport, true, zoneName);
        } else {
            ReportDatabase reportDatabase = ReportDatabase.getInstance();
            startFrag = new ReportInfoFrag(reportDatabase.getReportByZoneId(zoneId), zoneName);
        }
        ft.replace(R.id.report_frag_container, startFrag);
        ft.commit();

        closeBtn.setOnClickListener(v -> finish());
    }
}