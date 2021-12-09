package com.android.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ListView;

import com.android.asm2.R;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.ZoneListFrag;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ZoneListFrag zoneListFrag = new ZoneListFrag();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_frag_container, zoneListFrag);
        ft.commit();
    }
}