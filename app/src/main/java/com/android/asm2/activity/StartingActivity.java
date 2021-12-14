package com.android.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.asm2.helper.NotificationHelper;
import com.android.asm2.R;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.controller.ReportController;
import com.android.asm2.controller.UserController;
import com.android.asm2.controller.ZoneController;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        NotificationHelper.init(MainActivity.class, this);
        UserController.init(this);
        UserController.getAllUsers();
        ZoneController.init(this);
        ZoneController.getAllZones();
        ReportController.init(this);
        ReportController.getAllReports();

        ZoneDatabase zoneDatabase = ZoneDatabase.getInstance();
        ArrayList<Zone> zoneArrayList = zoneDatabase.getAllZones();

        ListView zoneListContainer = findViewById(R.id.list_adapter_container);
        ZoneAdapter adapter = new ZoneAdapter(this, zoneArrayList, true);
        zoneListContainer.setAdapter(adapter);
    }
}