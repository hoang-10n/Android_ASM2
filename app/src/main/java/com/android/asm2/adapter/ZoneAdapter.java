package com.android.asm2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.model.Zone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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
        TextView timeTxt = view.findViewById(R.id.zone_adapter_time_txt);
        TextView startTxt = view.findViewById(R.id.zone_adapter_start_txt);
        ImageButton mapBtn = view.findViewById(R.id.zone_adapter_map_btn);

        Zone zone = (Zone) getItem(i);
        String startTimeStr = zone.getStartTime();
        String endTimeStr = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
            Date date = dateFormat.parse(startTimeStr);
            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, (int) (zone.getDuration() * 60));
            endTimeStr = dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        nameTxt.setText(zone.getName());
        leaderTxt.setText(zone.getLeader());
        closedTxt.setText("Closed: " + zone.getClosedDate());
        timeTxt.setText(startTimeStr + " - " + endTimeStr);
        startTxt.setText("Start: " + zone.getStartDate());
        mapBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ZoneInfoActivity.class);
            intent.putExtra("id", zone.getId());
            context.startActivity(intent);
        });

        return view;
    }
}
