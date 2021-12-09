package com.android.asm2.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.asm2.R;
import com.android.asm2.ZoneDialog;
import com.android.asm2.activity.HomeActivity;
import com.android.asm2.adapter.ZoneAdapter;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ZoneListFrag extends Fragment {
    ArrayList<Zone> zoneArrayList = null;

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
        View view = inflater.inflate(R.layout.fragment_zone_list, container, false);
        Context context = getContext();

        ListView zoneListContainer = view.findViewById(R.id.zone_list_adapter_container);
        ZoneAdapter adapter = new ZoneAdapter(context, zoneArrayList);
        zoneListContainer.setAdapter(adapter);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(String toString, String toString1, boolean b, int i, boolean[] filterParams) {
        ((HomeActivity) requireActivity()).setData(toString, toString1, b, i, filterParams);
    }
}