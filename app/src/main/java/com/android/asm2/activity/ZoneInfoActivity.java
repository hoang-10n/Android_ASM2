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
import com.android.asm2.fragment.ZoneMapFrag;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

/***
 * Zone Info Activity with Zone Info, Zone Edit and Zone Map Fragments
 */
public class ZoneInfoActivity extends AppCompatActivity {
    private ZoneDatabase zoneDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        ImageButton close = findViewById(R.id.blank_close_btn);

        zoneDatabase = ZoneDatabase.getInstance();

        Intent intent = getIntent();
        int startPage = (int) intent.getExtras().get("startPage");
        String zoneId = (String) intent.getExtras().get("id");
        Fragment startingFrag;

        if (startPage == 0) {
            Zone zone = zoneDatabase.getZoneById(zoneId);
            startingFrag = new ZoneMapFrag(zone);
        } else if (startPage == 1) {
            Zone zone = zoneDatabase.getZoneById(zoneId);
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

    public void changeToInfoFrag(Zone zone) {
        ZoneInfoFrag zoneInfoFrag = new ZoneInfoFrag(zone);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, zoneInfoFrag);
        ft.commit();
    }

    public void changeToMapFrag(Zone zone) {
        ZoneMapFrag zoneMapFrag = new ZoneMapFrag(zone);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.blank_frag_container, zoneMapFrag);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Zone zone = zoneDatabase.getZoneById((String) bundle.get("id"));
                float newLat = Double.valueOf((double) bundle.get("newLat")).floatValue();
                float newLong = Double.valueOf((double) bundle.get("newLong")).floatValue();
                zone.setLatitude(newLat);
                zone.setLongitude(newLong);
                changeToEditFrag(zone, false);
            }
        }
    }
}