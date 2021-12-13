package com.android.asm2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.activity.ReportActivity;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Report;
import com.android.asm2.model.Zone;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Report> reportArrayList;

    public ReportAdapter(Context context, ArrayList<Report> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
    }

    @Override
    public int getCount() {
        return reportArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return reportArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_report, viewGroup, false);
        TextView idTxt = view.findViewById(R.id.report_adapter_zone_txt);
        TextView createdTxt = view.findViewById(R.id.report_adapter_created_txt);
        ImageButton viewBtn = view.findViewById(R.id.report_adapter_view_btn);

        Report report = (Report) getItem(i);
        ZoneDatabase zoneDatabase = ZoneDatabase.getInstance();
        Zone zone = zoneDatabase.getZoneById(report.getZoneId());
        idTxt.setText(report.getZoneId() + ": " + zone.getName());
        createdTxt.setText("Created: " + report.getCreated());

        viewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReportActivity.class);
            intent.putExtra("isAdded", false);
            intent.putExtra("zoneId", report.getZoneId());
            intent.putExtra("zoneName", zone.getName());
            context.startActivity(intent);
        });
        return view;
    }
}
