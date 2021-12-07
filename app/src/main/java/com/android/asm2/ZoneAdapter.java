package com.android.asm2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class ZoneAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Zone> zoneArrayList;

    public ZoneAdapter(Context context, ArrayList<Zone> zoneArrayList) {
        this.context = context;
        this.zoneArrayList = zoneArrayList;
    }

    @Override
    public int getCount() {
        return zoneArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return zoneArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).
                inflate(R.layout.adapter_zone, viewGroup, false);
        TextView nameTxt = view.findViewById(R.id.zone_adapter_name_txt);
        TextView leaderTxt = view.findViewById(R.id.zone_adapter_leader_txt);
        TextView closedTxt = view.findViewById(R.id.zone_adapter_closed_txt);
        TextView durationTxt = view.findViewById(R.id.zone_adapter_duration_txt);
        TextView startTxt = view.findViewById(R.id.zone_adapter_start_txt);
        ImageButton mapBtn = view.findViewById(R.id.zone_adapter_map_btn);

        Zone zone = (Zone) getItem(i);
        nameTxt.setText(zone.getName());
        leaderTxt.setText(zone.getLeader());
        closedTxt.setText(zone.getClosedDate());
        durationTxt.setText(String.valueOf(zone.getDuration()));
        startTxt.setText(zone.getStartDate());
        mapBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("id", zone.getId());
        });

        return view;
    }
}
