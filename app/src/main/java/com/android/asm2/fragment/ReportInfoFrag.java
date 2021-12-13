package com.android.asm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.activity.ReportActivity;
import com.android.asm2.model.Report;

public class ReportInfoFrag extends Fragment {
    private Report report;
    private String zoneName;

    public ReportInfoFrag(Report report, String zoneName) {
        this.report = report;
        this.zoneName = zoneName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_info, container, false);
        TextView nameTxt = view.findViewById(R.id.report_info_frag_zone_txt);
        TextView testedTxt = view.findViewById(R.id.report_info_frag_tested_txt);
        TextView volunteerTxt = view.findViewById(R.id.report_info_frag_volunteer_txt);
        TextView sampleTxt = view.findViewById(R.id.report_info_frag_sample_txt);
        TextView positive1stTxt = view.findViewById(R.id.report_info_frag_positive_1st_txt);
        TextView positiveTxt = view.findViewById(R.id.report_info_frag_positive_txt);
        TextView noteTxt = view.findViewById(R.id.report_info_frag_note_txt);
        Button editBtn = view.findViewById(R.id.report_info_frag_edit_btn);

        nameTxt.setText(report.getZoneId() + ": " + zoneName);
        testedTxt.setText(report.getTested() + "");
        volunteerTxt.setText(report.getVolunteer() + "");
        sampleTxt.setText(report.getSample() + "");
        positive1stTxt.setText(report.getPositive1st() + "");
        positiveTxt.setText(report.getPositive() + "");
        noteTxt.setText(report.getNote());

        editBtn.setOnClickListener(v -> ((ReportActivity) requireActivity()).setEditFrag(report, zoneName));
        return view;
    }
}