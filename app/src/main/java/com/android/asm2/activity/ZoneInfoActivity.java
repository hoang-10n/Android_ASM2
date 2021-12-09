package com.android.asm2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.asm2.R;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.ZoneEditFrag;
import com.android.asm2.fragment.ZoneInfoFrag;
import com.android.asm2.model.Zone;

public class ZoneInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_info);
        ImageButton close = findViewById(R.id.zone_info_close_btn);

        Intent intent = getIntent();
        String zoneId = (String) intent.getExtras().get("id");
//
//        ZoneDatabase database = new ZoneDatabase(this);
//        Zone zone = database.getZoneById(zoneId);
        ZoneEditFrag zoneInfoFrag = new ZoneEditFrag();
//        zoneInfoFrag.setZone(zone);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.zone_info_frag_container, zoneInfoFrag);
        ft.commit();

        close.setOnClickListener(v -> finish());
    }
}