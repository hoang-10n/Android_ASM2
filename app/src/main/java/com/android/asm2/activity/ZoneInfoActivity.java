package com.android.asm2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.asm2.R;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.ZoneEditFrag;
import com.android.asm2.fragment.ZoneInfoFrag;
import com.android.asm2.model.Zone;

public class ZoneInfoActivity extends AppCompatActivity {
    private Fragment startingFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_info);
        ImageButton close = findViewById(R.id.zone_info_close_btn);

        Intent intent = getIntent();
        boolean isAdded = (boolean) intent.getExtras().get("isAdded");
        String zoneId = (String) intent.getExtras().get("id");
        String leader = (String) intent.getExtras().get("leader");
        if (!isAdded) {
            boolean joined = (boolean) intent.getExtras().get("joined");
            ZoneDatabase database = new ZoneDatabase(this);
            Zone zone = database.getZoneById(zoneId);
            startingFrag = new ZoneInfoFrag(zone, leader.equals(zone.getLeader()), joined);
        } else {
            Zone zone = new Zone(zoneId, leader);
            startingFrag = new ZoneEditFrag(zone, true);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.zone_info_frag_container, startingFrag);
        ft.commit();

        close.setOnClickListener(v -> finish());
    }

    public void changeToEditFrag(Zone zone, boolean isAdded) {
        ZoneEditFrag zoneEditFrag = new ZoneEditFrag(zone, isAdded);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.zone_info_frag_container, zoneEditFrag);
        ft.commit();
    }

    public void changeToInfoFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.zone_info_frag_container, startingFrag);
        ft.commit();
    }
}