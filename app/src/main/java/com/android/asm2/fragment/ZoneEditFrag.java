package com.android.asm2.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.asm2.R;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

public class ZoneEditFrag extends Fragment {
    private final Zone zone;
    private final boolean isAdded;
    private final String[] closedDateStr, startDateStr, startTimeStr;
    private EditText nameInput, durationInput, descriptionInput, closedDateInput,
            startDateInput, startTimeInput;
    private TextView latTxt, longTxt, errorTxt;

    public ZoneEditFrag(Zone zone, boolean isAdded) {
        this.zone = zone;
        this.isAdded = isAdded;
        closedDateStr = new String[1];
        startDateStr = new String[1];
        startTimeStr = new String[1];
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
        TextView leaderTxt = view.findViewById(R.id.zone_edit_frag_leader_txt);
        TextView createdTxt = view.findViewById(R.id.zone_edit_frag_created_txt);
        latTxt = view.findViewById(R.id.zone_edit_frag_latitude_txt);
        longTxt = view.findViewById(R.id.zone_edit_frag_longitude_txt);
        nameInput = view.findViewById(R.id.zone_edit_frag_name_input);
        durationInput = view.findViewById(R.id.zone_edit_frag_duration_input);
        closedDateInput = view.findViewById(R.id.zone_edit_frag_closed_input);
        startDateInput = view.findViewById(R.id.zone_edit_frag_start_date_input);
        startTimeInput = view.findViewById(R.id.zone_edit_frag_start_time_input);
        descriptionInput = view.findViewById(R.id.zone_edit_frag_description_input);
        ImageButton backBtn = view.findViewById(R.id.zone_edit_frag_back_btn);
        Button saveBtn = view.findViewById(R.id.zone_edit_frag_save_btn);
        Button restoreBtn = view.findViewById(R.id.zone_edit_frag_restore_btn);
        Button deleteBtn = view.findViewById(R.id.zone_edit_frag_delete_btn);
        errorTxt = view.findViewById(R.id.zone_edit_frag_error_txt);

        if (isAdded) backBtn.setVisibility(View.GONE);

        leaderTxt.setText("created by " + zone.getLeader());
        createdTxt.setText(zone.getCreatedDate());
        setInput();

        closedDateInput.setOnClickListener(v ->
                setDatePickerDialog(closedDateInput, closedDateStr));
        startDateInput.setOnClickListener(v ->
                setDatePickerDialog(startDateInput, startDateStr));
        startTimeInput.setOnClickListener(v -> {
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR);
            int minute = time.get(Calendar.MINUTE);

            @SuppressLint("SetTextI18n") TimePickerDialog dialog = new TimePickerDialog(
                    requireActivity(),
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    (timePicker, hour1, minute1) -> {
                        startTimeStr[0] = String.format(Locale.US, "%02d", hour1) + ":" +
                                String.format(Locale.US, "%02d", minute1);
                        startTimeInput.setText(startTimeStr[0]);
                    },
                    hour, minute, true
            );
            dialog.setTitle("Select start time:");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        backBtn.setOnClickListener(v -> ((ZoneInfoActivity) requireActivity()).changeToInfoFrag());
        restoreBtn.setOnClickListener(v -> setInput());
        saveBtn.setOnClickListener((v -> saveZone()));
        deleteBtn.setOnClickListener(v -> {
            ZoneDatabase zoneDatabase = ZoneDatabase.getInstance();
            zoneDatabase.deleteZoneById(zone.getId());
            requireActivity().finish();
        });

        return view;
    }

    private void setDatePickerDialog(EditText dateInput, String[] str) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SetTextI18n") DatePickerDialog dialog = new DatePickerDialog(
                requireActivity(),
                android.R.style.Theme_Holo_Dialog_MinWidth,
                (datePicker, year1, month1, day1) -> {
                    str[0] = year1 + "-" + String.format(Locale.US, "%02d", month1 + 1)
                            + "-" + String.format(Locale.US, "%02d", day1);
                    dateInput.setText(str[0]);
                },
                year, month, day
        );
        dialog.setTitle("Select closed date:");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setInput() {
        latTxt.setText("Latitude: " + zone.getLatitude() + "\"N");
        longTxt.setText("Longitude: " + zone.getLongitude() + "\"E");
        nameInput.setText(zone.getName());
        durationInput.setText(zone.getDuration() + "");
        closedDateInput.setText(zone.getClosedDate());
        startDateInput.setText(zone.getStartDate());
        startTimeInput.setText(zone.getStartTime());
        descriptionInput.setText(zone.getDescription());
    }

    private boolean isEmptyInput() {
        String nameStrCheck = nameInput.getText().toString().trim();
        String durationStrCheck = durationInput.getText().toString().trim();
        String closedDateStrCheck = closedDateInput.getText().toString().trim();
        String startDateStrCheck = startDateInput.getText().toString().trim();
        String startTimeStrCheck = startTimeInput.getText().toString().trim();

        if (nameStrCheck.equals("") || durationStrCheck.equals("") ||
                closedDateStrCheck.equals("") || startDateStrCheck.equals("") ||
                startTimeStrCheck.equals("")) {
            errorTxt.setVisibility(View.VISIBLE);
            return true;
        } else {
            errorTxt.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    // TODO change lat and long when you add map
    private void saveZone() {
        if (isEmptyInput()) return;
        String nameStr = nameInput.getText().toString().trim();
        String durationStr = durationInput.getText().toString();
        float durationFloat = durationStr.equals(".") ? 0 : Float.parseFloat(durationStr);
        Zone saveZone = new Zone(zone.getId(), nameStr, zone.getLatitude(), zone.getLongitude(),
                durationFloat, zone.getQuantity(), zone.getLeader(), zone.getCreatedDate(),
                closedDateStr[0], startDateStr[0], startTimeStr[0],
                descriptionInput.getText().toString());
        ZoneDatabase zoneDatabase = ZoneDatabase.getInstance();
        if (isAdded) zoneDatabase.addZone(saveZone);
        else zoneDatabase.updateZone(saveZone);
        requireActivity().finish();
    }
}