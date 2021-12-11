package com.android.asm2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.model.Zone;

public class ZoneInfoFrag extends Fragment {
    private final Zone zone;
    private final boolean isLeader, joined;

    public ZoneInfoFrag(Zone zone, boolean isLeader, boolean joined) {
        this.zone = zone;
        this.isLeader = isLeader;
        this.joined = joined;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
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
        Button multipleBtn = view.findViewById(R.id.zone_info_frag_multiple_btn);
        Button friendBtn = view.findViewById(R.id.zone_info_frag_friend_btn);

        nameTxt.setText(zone.getName());
        durationTxt.setText("Duration: " + zone.getDuration() + "hrs");
        quantityTxt.setText(zone.getQuantity() + " volunteers");
        createdTxt.setText("Created: " + zone.getCreatedDate());
        closedTxt.setText("Closed: " + zone.getClosedDate());
        startTxt.setText("Start: " + zone.getStartDate() + " at " + zone.getStartTime());
        leaderTxt.setText("created by " + zone.getLeader());
        descriptionTxt.setText(" - " + zone.getDescription());

        if (isLeader) {
            multipleBtn.setText("Edit this zone");
            multipleBtn.setOnClickListener(v ->
                    ((ZoneInfoActivity) requireActivity()).changeToEditFrag(zone, false));
        } else if (joined) {
            multipleBtn.setText("Leave this zone");
        } else {
            multipleBtn.setText("Enter this zone");
        }

        return view;
    }
}