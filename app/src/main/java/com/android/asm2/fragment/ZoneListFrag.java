package com.android.asm2.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.asm2.R;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class ZoneListFrag extends Fragment {
    private final ArrayList<Zone> zoneArrayList;

    public ZoneListFrag(ArrayList<Zone> zoneArrayList) {
        this.zoneArrayList = zoneArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView zoneListContainer = view.findViewById(R.id.list_adapter_container);
        ZoneAdapter adapter = new ZoneAdapter(getContext(), zoneArrayList, false);
        zoneListContainer.setAdapter(adapter);

        return view;
    }
}