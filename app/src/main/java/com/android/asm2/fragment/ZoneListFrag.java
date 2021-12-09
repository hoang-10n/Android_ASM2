package com.android.asm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.asm2.R;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class ZoneListFrag extends Fragment {
    public ZoneListFrag() {
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
        View view = inflater.inflate(R.layout.fragment_zone_list, container, false);

        ZoneDatabase database = new ZoneDatabase(getContext());
        ArrayList<Zone> zoneArrayList = database.getAllZones();
        ZoneAdapter adapter = new ZoneAdapter(getContext(), zoneArrayList);
        ListView zoneListContainer = view.findViewById(R.id.zone_list_adapter_container);
        zoneListContainer.setAdapter(adapter);

        return view;
    }
}