package com.android.asm2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.model.Zone;

public class ZoneInfoFrag extends Fragment {
    private Zone zone = null;

    public ZoneInfoFrag(Zone zone) {
        this.zone = zone;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone_info, container, false);
        TextView nameTxt = view.findViewById(R.id.zone_info_frag_name_txt);
        TextView durationTxt = view.findViewById(R.id.zone_info_frag_duration_txt);
        TextView quantityTxt = view.findViewById(R.id.zone_info_frag_quantity_txt);
        TextView createdTxt = view.findViewById(R.id.zone_info_frag_created_txt);
        TextView closedTxt = view.findViewById(R.id.zone_info_frag_closed_txt);
        TextView startTxt = view.findViewById(R.id.zone_info_frag_start_txt);
        TextView leaderTxt = view.findViewById(R.id.zone_info_frag_leader_txt);
        TextView descriptionTxt = view.findViewById(R.id.zone_info_frag_description_txt);

        nameTxt.setText(zone.getName());
        durationTxt.setText("Duration: " + zone.getDuration() + "hrs");
        quantityTxt.setText(zone.getQuantity() + " volunteers");
        createdTxt.setText("Created: " + zone.getCreatedDate());
        closedTxt.setText("Closed: " + zone.getClosedDate());
        startTxt.setText("Start: " + zone.getStartDate() + " at " + zone.getStartTime());
        leaderTxt.setText("created by " + zone.getLeader());
        descriptionTxt.setText(" - " + zone.getDescription());

        return view;
    }
}