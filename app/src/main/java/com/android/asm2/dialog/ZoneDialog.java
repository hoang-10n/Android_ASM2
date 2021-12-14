package com.android.asm2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.android.asm2.R;
import com.android.asm2.activity.HomeActivity;

public class ZoneDialog extends Dialog {
    private final int[] sortParam = {0};
    private final boolean[] isAscending = {true};

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ZoneDialog(Context context, int themeResId, HomeActivity activity) {
        super(context, themeResId);
        setContentView(R.layout.popup_zone_param);
        getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent);
        setTitle("Search, sort, filter zones");

        String[] sortParams = {"Closed date", "Start date", "Start time",
                "Name", "Quantity", "Duration"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_adapter, sortParams);

        EditText nameInput = findViewById(R.id.popup_zone_list_name_input);
        EditText leaderInput = findViewById(R.id.popup_zone_list_leader_input);
        Button closeBtn = findViewById(R.id.popup_zone_list_close_btn);
        Button applyBtn = findViewById(R.id.popup_zone_list_apply_btn);
        Button resetBtn = findViewById(R.id.popup_zone_list_reset_btn);
        ImageButton sortDirectionBtn = findViewById(R.id.popup_zone_list_sort_direction_btn);
        CheckBox showJoinCheck = findViewById(R.id.popup_zone_list_join_check);
        CheckBox showLeadingCheck = findViewById(R.id.popup_zone_list_leading_check);
        CheckBox hideClosed = findViewById(R.id.popup_zone_list_closed_check);
        CheckBox hideStarted = findViewById(R.id.popup_zone_list_started_check);

        Spinner sortSpinner = findViewById(R.id.popup_zone_list_sort_spinner);
        sortSpinner.setAdapter(sortAdapter);

        closeBtn.setOnClickListener(v1 -> dismiss());
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortParam[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        resetBtn.setOnClickListener(v -> {
            nameInput.setText("");
            leaderInput.setText("");
            sortSpinner.setSelection(0);
            showJoinCheck.setChecked(false);
            showLeadingCheck.setChecked(false);
            hideClosed.setChecked(false);
            hideStarted.setChecked(false);
        });

        applyBtn.setOnClickListener(v -> {
            boolean[] filterParams = {showJoinCheck.isChecked(), showLeadingCheck.isChecked(),
                    hideClosed.isChecked(), hideStarted.isChecked()};
            activity.setData(nameInput.getText().toString().trim(),
                    leaderInput.getText().toString().trim(),
                    isAscending[0], sortParam[0], filterParams);
            dismiss();
        });

        sortDirectionBtn.setOnClickListener(v -> {
            sortDirectionBtn.setImageResource(isAscending[0] ? android.R.drawable.arrow_down_float :
                    android.R.drawable.arrow_up_float);
            isAscending[0] = !isAscending[0];
        });
    }
}
