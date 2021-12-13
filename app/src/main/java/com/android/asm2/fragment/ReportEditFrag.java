package com.android.asm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.activity.ReportActivity;
import com.android.asm2.database.ReportDatabase;
import com.android.asm2.model.Report;

public class ReportEditFrag extends Fragment {
    private Report report;
    private boolean isAdded;
    private String zoneName;
    private EditText testedInput, volunteerInput, sampleInput, positive1stInput, positiveInput, noteInput;

    public ReportEditFrag(Report report, boolean isAdded, String zoneName) {
        this.report = report;
        this.isAdded = isAdded;
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
        View view = inflater.inflate(R.layout.fragment_report_edit, container, false);
        TextView nameTxt = view.findViewById(R.id.report_edit_frag_zone_txt);
        testedInput = view.findViewById(R.id.report_edit_frag_tested_input);
        volunteerInput = view.findViewById(R.id.report_edit_frag_volunteer_input);
        sampleInput = view.findViewById(R.id.report_edit_frag_sample_input);
        positive1stInput = view.findViewById(R.id.report_edit_frag_positive_1st_input);
        positiveInput = view.findViewById(R.id.report_edit_frag_positive_input);
        noteInput = view.findViewById(R.id.report_edit_frag_note_input);
        Button saveBtn = view.findViewById(R.id.report_edit_frag_save_btn);
        Button discardBtn = view.findViewById(R.id.report_edit_frag_discard_btn);

        nameTxt.setText(report.getZoneId() + ": " + zoneName);
        setInput();

        saveBtn.setOnClickListener(v -> {
            report.setTested(getIntegerInput(testedInput));
            report.setVolunteer(getIntegerInput(volunteerInput));
            report.setSample(getIntegerInput(sampleInput));
            report.setPositive(getIntegerInput(positiveInput));
            report.setPositive1st(getIntegerInput(positive1stInput));
            report.setNote(noteInput.getText().toString());
            ReportDatabase reportDatabase = ReportDatabase.getInstance();
            if (isAdded) reportDatabase.addReport(report);
            else reportDatabase.updateReport(report);
            ((ReportActivity) requireActivity()).setInfoFrag(report, zoneName);
        });

        discardBtn.setOnClickListener(v -> {
            setInput();
            if (!isAdded) ((ReportActivity) requireActivity()).setInfoFrag(report, zoneName);
        });
        return view;
    }

    private void setInput() {
        testedInput.setText(report.getTested() + "");
        volunteerInput.setText(report.getVolunteer() + "");
        sampleInput.setText(report.getSample() + "");
        positive1stInput.setText(report.getPositive1st() + "");
        positiveInput.setText(report.getPositive() + "");
        noteInput.setText(report.getNote());
    }

    private int getIntegerInput(EditText editText) {
        String value = editText.getText().toString();
        return value.equals("") ? 0 : Integer.parseInt(value);
    }
}