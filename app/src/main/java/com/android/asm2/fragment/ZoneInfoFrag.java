package com.android.asm2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.asm2.controller.UserController;
import com.android.asm2.dialog.ActionDialog;
import com.android.asm2.dialog.FriendDialog;
import com.android.asm2.R;
import com.android.asm2.activity.ZoneInfoActivity;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

public class ZoneInfoFrag extends Fragment {
    private final Zone zone;
    private Button multipleBtn;
    private User user;

    public ZoneInfoFrag(Zone zone) {
        this.zone = zone;
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
        user = UserDatabase.getCurrentUser();

        multipleBtn = view.findViewById(R.id.zone_info_frag_multiple_btn);
        TextView nameTxt = view.findViewById(R.id.zone_info_frag_name_txt);
        TextView durationTxt = view.findViewById(R.id.zone_info_frag_duration_txt);
        TextView quantityTxt = view.findViewById(R.id.zone_info_frag_quantity_txt);
        TextView createdTxt = view.findViewById(R.id.zone_info_frag_created_txt);
        TextView closedTxt = view.findViewById(R.id.zone_info_frag_closed_txt);
        TextView startTxt = view.findViewById(R.id.zone_info_frag_start_txt);
        TextView leaderTxt = view.findViewById(R.id.zone_info_frag_leader_txt);
        TextView descriptionTxt = view.findViewById(R.id.zone_info_frag_description_txt);
        Button friendBtn = view.findViewById(R.id.zone_info_frag_friend_btn);
        ImageButton mapBtn = view.findViewById(R.id.zone_info_frag_map_btn);

        nameTxt.setText(zone.getName());
        durationTxt.setText("Duration: " + zone.getDuration() + "hrs");
        quantityTxt.setText(zone.getQuantity() + " volunteers");
        createdTxt.setText("Created: " + zone.getCreatedDate());
        closedTxt.setText("Closed: " + zone.getClosedDate());
        startTxt.setText("Start: " + zone.getStartDate() + " at " + zone.getStartTime());
        leaderTxt.setText("created by " + zone.getLeader());
        descriptionTxt.setText(" - " + zone.getDescription());

        if (user.getUsername().equals(zone.getLeader()) || user.getRole().equals("admin")) {
            multipleBtn.setText("Show actions");
            multipleBtn.setOnClickListener(v -> {
                ActionDialog actionDialog = new ActionDialog(getContext(),
                        android.R.style.Theme_Dialog, (ZoneInfoActivity) requireActivity(), zone);
                actionDialog.show();
            });
        } else if (user.isJoinedZone(zone.getId())) {
            setToLeaveBtn();
        } else {
            setToJoinBtn();
        }

        friendBtn.setOnClickListener(v -> {
            FriendDialog friendDialog = new FriendDialog(getContext(),
                    android.R.style.Theme_Dialog, zone);
            friendDialog.show();
        });

        mapBtn.setOnClickListener(v -> {
            ((ZoneInfoActivity) requireActivity()).changeToMapFrag(zone);
        });
        return view;
    }

    private void setToJoinBtn() {
        multipleBtn.setText("Enter this zone");
        multipleBtn.setOnClickListener(v -> {
            user.joinZone(zone.getId());
            UserController.updateUser(user);
            setToLeaveBtn();
        });
    }

    private void setToLeaveBtn() {
        multipleBtn.setText("Leave this zone");
        multipleBtn.setOnClickListener(v -> {
            user.leaveZone(zone.getId());
            UserController.updateUser(user);
            setToJoinBtn();
        });
    }
}