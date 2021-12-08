package com.android.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.asm2.R;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ZoneDatabase database = new ZoneDatabase(this);
        ArrayList<Zone> zoneArrayList = database.getAllZones();
        Log.d("TAG", zoneArrayList.toString());
        ZoneAdapter adapter = new ZoneAdapter(this, zoneArrayList);
        ListView view = findViewById(R.id.test_adapter);
        view.setAdapter(adapter);
    }
}