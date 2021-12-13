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
        setContentView(R.layout.activity_blank);
        ImageButton closeBtn = findViewById(R.id.blank_close_btn);

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
        ft.replace(R.id.blank_frag_container, startFrag);
        ft.commit();

        closeBtn.setOnClickListener(v -> finish());
    }

    public void setInfoFrag(Report report, String zoneName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ReportInfoFrag reportInfoFrag = new ReportInfoFrag(report, zoneName);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, reportInfoFrag);
        ft.commit();
    }

    public void setEditFrag(Report report, String zoneName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ReportEditFrag reportEditFrag = new ReportEditFrag(report, false, zoneName);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, reportEditFrag);
        ft.commit();
    }
}