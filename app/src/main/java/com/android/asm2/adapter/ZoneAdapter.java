package com.android.asm2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.asm2.R;
import com.android.asm2.activity.MainActivity;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ZoneAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Zone> zoneArrayList;
    private final boolean isStarting;

    public ZoneAdapter(Context context, ArrayList<Zone> zoneArrayList, boolean isStarting) {
        this.context = context;
        this.zoneArrayList = zoneArrayList;
        this.isStarting = isStarting;
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

    @SuppressLint({"ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_zone, viewGroup, false);
        TextView nameTxt = view.findViewById(R.id.zone_adapter_name_txt);
        TextView leaderTxt = view.findViewById(R.id.zone_adapter_leader_txt);
        TextView closedTxt = view.findViewById(R.id.zone_adapter_closed_txt);
        TextView timeTxt = view.findViewById(R.id.zone_adapter_time_txt);
        TextView startTxt = view.findViewById(R.id.zone_adapter_start_txt);
        ImageButton mapBtn = view.findViewById(R.id.zone_adapter_map_btn);

        Zone zone = (Zone) getItem(i);
        String startDateStr = zone.getStartDate();
        String closedDateStr = zone.getClosedDate();
        String startTimeStr = zone.getStartTime();
        String endTimeStr = "";
        boolean isClosed = false, isStarted = false;

        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date curDate = new Date();
            isClosed = dateFormat.parse(closedDateStr).compareTo(curDate) < 0;
            isStarted = dateFormat.parse(startDateStr).compareTo(curDate) < 0;

            Date startTime = timeFormat.parse(startTimeStr);
            Calendar calendar = Calendar.getInstance();
            assert startTime != null;
            calendar.setTime(startTime);
            calendar.add(Calendar.MINUTE, (int) (zone.getDuration() * 60));
            endTimeStr = timeFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        nameTxt.setText(zone.getId() + ": " + zone.getName());
        leaderTxt.setText(zone.getLeader());
        closedTxt.setText("Closed: " + zone.getClosedDate());
        timeTxt.setText(startTimeStr + " - " + endTimeStr);
        startTxt.setText("Start: " + zone.getStartDate());

        if (isClosed) closedTxt.setTextColor(Color.parseColor("#FF0000"));
        if (isStarted) startTxt.setTextColor(Color.parseColor("#FF0000"));

        mapBtn.setOnClickListener(v -> {
            Intent intent;
            if (!isStarting) {
                intent = new Intent(context, ZoneInfoActivity.class);
                intent.putExtra("id", zone.getId());
                intent.putExtra("startPage", 0);
//                ((AppCompatActivity) context).startActivityForResult(intent, 100);
            } else {
                intent = new Intent(context, MainActivity.class);
            }
            context.startActivity(intent);
        });

        return view;
    }
}
