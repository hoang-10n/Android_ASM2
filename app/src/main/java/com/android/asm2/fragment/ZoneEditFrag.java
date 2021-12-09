package com.android.asm2.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.asm2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ZoneEditFrag extends Fragment {
    public ZoneEditFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone_edit, container, false);
        EditText closedInput = view.findViewById(R.id.zone_edit_frag_closed_input);
        EditText startDateInput = view.findViewById(R.id.zone_edit_frag_start_date_input);
        EditText startTimeInput = view.findViewById(R.id.zone_edit_frag_start_time_input);

        closedInput.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    requireActivity(),
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    (datePicker, year1, month1, day1) -> closedInput.setText(year1 + "-" + month1 + "-" + day1),
                    year, month, day
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        startDateInput.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    requireActivity(),
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    (datePicker, year1, month1, day1) -> startDateInput.setText(year1 + "-" + month1 + "-" + day1),
                    year, month, day
            );
            dialog.setTitle("Select closed date:");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        startTimeInput.setOnClickListener(v -> {
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR);
            int minute = time.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(
                    requireActivity(),
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    (timePicker, i, i1) -> startTimeInput.setText(i + ":" + i1),
                    hour, minute, true
            );
            dialog.setTitle("Select start time:");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });


        return view;
    }
}