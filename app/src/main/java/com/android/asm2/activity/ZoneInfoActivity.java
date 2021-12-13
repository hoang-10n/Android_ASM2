package com.android.asm2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.asm2.R;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.ZoneEditFrag;
import com.android.asm2.fragment.ZoneInfoFrag;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

public class ZoneInfoActivity extends AppCompatActivity {
    private Fragment startingFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        ImageButton close = findViewById(R.id.blank_close_btn);

        Intent intent = getIntent();
        boolean isAdded = (boolean) intent.getExtras().get("isAdded");
        String zoneId = (String) intent.getExtras().get("id");
        if (!isAdded) {
            ZoneDatabase database = ZoneDatabase.getInstance();
            Zone zone = database.getZoneById(zoneId);
            startingFrag = new ZoneInfoFrag(zone);
        } else {
            User user = UserDatabase.getCurrentUser();
            Zone zone = new Zone(zoneId, user.getUsername());
            startingFrag = new ZoneEditFrag(zone, true);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.blank_frag_container, startingFrag);
        ft.commit();

        close.setOnClickListener(v -> finish());
    }

    public void changeToEditFrag(Zone zone, boolean isAdded) {
        ZoneEditFrag zoneEditFrag = new ZoneEditFrag(zone, isAdded);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, zoneEditFrag);
        ft.commit();
    }

    public void changeToInfoFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, startingFrag);
        ft.commit();
    }
}