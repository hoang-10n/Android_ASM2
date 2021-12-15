package com.android.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.asm2.R;
import com.android.asm2.database.ReportDatabase;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.ReportListFrag;
import com.android.asm2.fragment.UserListFrag;
import com.android.asm2.fragment.ZoneListFrag;
import com.android.asm2.model.Report;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

import java.util.ArrayList;

/***
 * Home Activity for Admin wiht Zone List, Report List and User List Fragment
 */
public class AdminActivity extends AppCompatActivity {
    private ArrayList<Zone> zoneArrayList;
    private ArrayList<User> userArrayList;
    private ArrayList<Report> reportArrayList;
    private ZoneListFrag zoneListFrag;
    private UserListFrag userListFrag;
    private ReportListFrag reportListFrag;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ImageButton searchBtn = findViewById(R.id.admin_search_btn);
        Button logoutBtn = findViewById(R.id.admin_logout_btn);
        Button zoneBtn = findViewById(R.id.admin_zone_btn);
        Button userBtn = findViewById(R.id.admin_user_btn);
        Button reportBtn = findViewById(R.id.admin_report_btn);
        EditText searchInput = findViewById(R.id.admin_search_input);

        ZoneDatabase zoneDatabase = ZoneDatabase.getInstance();
        zoneArrayList = zoneDatabase.getAllZones();
        UserDatabase userDatabase = UserDatabase.getInstance();
        userArrayList = userDatabase.getAllUsers();
        ReportDatabase reportDatabase = ReportDatabase.getInstance();
        reportArrayList = reportDatabase.getAllReports();

        zoneListFrag = new ZoneListFrag(zoneArrayList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.admin_frag_container, zoneListFrag);
        ft.commit();

        zoneBtn.setOnClickListener(v -> {
            page = 0;
            zoneBtn.setEnabled(false);
            userBtn.setEnabled(true);
            reportBtn.setEnabled(true);
            searchInput.setHint("Zone name");
            zoneListFrag = new ZoneListFrag(filterZones(searchInput.getText().toString().trim()));
            setFragment(zoneListFrag);
        });

        userBtn.setOnClickListener(v -> {
            page = 1;
            zoneBtn.setEnabled(true);
            userBtn.setEnabled(false);
            reportBtn.setEnabled(true);
            searchInput.setHint("User's email");
            userListFrag = new UserListFrag(filterUsers(searchInput.getText().toString().trim()));
            setFragment(userListFrag);
        });

        reportBtn.setOnClickListener(v -> {
            page = 2;
            zoneBtn.setEnabled(true);
            userBtn.setEnabled(true);
            reportBtn.setEnabled(false);
            searchInput.setHint("Report's zone id");
            reportListFrag = new ReportListFrag(filterReports(searchInput.getText().toString().trim()));
            setFragment(reportListFrag);
        });

        searchBtn.setOnClickListener(v -> {
            Fragment fragment;
            if (page == 0) fragment = new ZoneListFrag(filterZones(searchInput.getText().toString().trim()));
            else if (page == 1) fragment = new UserListFrag(filterUsers(searchInput.getText().toString().trim()));
            else fragment = new ReportListFrag(filterReports(searchInput.getText().toString().trim()));
            setFragment(fragment);
        });

        logoutBtn.setOnClickListener(v -> finish());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft1.replace(R.id.admin_frag_container, fragment);
        ft1.commit();
    }

    private ArrayList<Zone> filterZones(String name) {
        ArrayList<Zone> filtered = new ArrayList<>();
        for (Zone zone : zoneArrayList) {
            if (zone.getName().contains(name)) filtered.add(zone);
        }
        return filtered;
    }

    private ArrayList<Report> filterReports(String zoneId) {
        ArrayList<Report> filtered = new ArrayList<>();
        for (Report report : reportArrayList) {
            if (report.getZoneId().contains(zoneId)) filtered.add(report);
        }
        return filtered;
    }

    private ArrayList<User> filterUsers(String email) {
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : userArrayList) {
            if (user.getName().contains(email)) filtered.add(user);
        }
        return filtered;
    }
}