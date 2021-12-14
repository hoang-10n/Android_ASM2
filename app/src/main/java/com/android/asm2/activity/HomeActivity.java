package com.android.asm2.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.asm2.R;
import com.android.asm2.controller.UserController;
import com.android.asm2.dialog.ZoneDialog;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.fragment.HomeMapFrag;
import com.android.asm2.fragment.UserInfoFrag;
import com.android.asm2.fragment.ZoneListFrag;
import com.android.asm2.model.User;
import com.android.asm2.model.Zone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private boolean isAscending = true;
    private String zoneName = "";
    private String zoneLeader = "";
    private int sortParam = 0;
    private boolean[] filterArray = {false, false, false, false};
    private User user;
    private ZoneListFrag zoneListFrag;
    private ArrayList<Zone> zoneArrayList;
    private ZoneDialog dialog;
    private ZoneDatabase zoneDatabase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button popupBtn = findViewById(R.id.home_zone_list_popup_btn);
        Button accountBtn = findViewById(R.id.home_account_btn);
        Button zoneBtn = findViewById(R.id.home_zone_btn);
        Button mapBtn = findViewById(R.id.home_map_btn);
        ImageButton addBtn = findViewById(R.id.home_zone_list_add_btn);

        user = UserDatabase.getCurrentUser();
        zoneDatabase = ZoneDatabase.getInstance();
        zoneArrayList = zoneDatabase.getAllZones();

        dialog = new ZoneDialog(this, android.R.style.Theme_Dialog, this);
        zoneListFrag = new ZoneListFrag(zoneArrayList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_frag_container, zoneListFrag);
        ft.commit();

        popupBtn.setOnClickListener(v -> dialog.show());
        addBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ZoneInfoActivity.class);
            String lastZoneId = zoneArrayList.get(zoneArrayList.size() - 1).getId();
            String newId = "Z" + (Integer.parseInt(lastZoneId.substring(1)) + 1);
            intent1.putExtra("id", newId);
            intent1.putExtra("startPage", 2);
            startActivityForResult(intent1, 100);
        });

        accountBtn.setOnClickListener(v -> {
            zoneBtn.setEnabled(true);
            accountBtn.setEnabled(false);
            mapBtn.setEnabled(true);
            addBtn.setVisibility(View.GONE);
            popupBtn.setVisibility(View.GONE);
            resetUserInfoFrag();
        });

        zoneBtn.setOnClickListener(v -> {
            zoneBtn.setEnabled(false);
            accountBtn.setEnabled(true);
            mapBtn.setEnabled(true);
            addBtn.setVisibility(View.VISIBLE);
            popupBtn.setVisibility(View.VISIBLE);
            resetZoneListFrag();
        });

        mapBtn.setOnClickListener(v -> {
            zoneBtn.setEnabled(true);
            accountBtn.setEnabled(true);
            mapBtn.setEnabled(false);
            addBtn.setVisibility(View.GONE);
            popupBtn.setVisibility(View.GONE);
            resetMapFrag();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<Zone> searchSortFilter(Object[] cloneArr) {
        ArrayList<Zone> clone = new ArrayList<>();
        for (Object i : cloneArr) clone.add((Zone) i);
        clone.sort((a, b) -> {
            try {
                return compareZone(a, b);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });

        filterZones(clone);
        return clone;
    }

    private int compareZone(Zone a, Zone b) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        int result = 0;
        switch (sortParam) {
            case 0:
                result = Objects.requireNonNull(dateFormat.parse(b.getClosedDate())).
                        compareTo(dateFormat.parse(a.getClosedDate()));
                break;
            case 1:
                result = Objects.requireNonNull(dateFormat.parse(b.getStartDate())).
                        compareTo(dateFormat.parse(a.getStartDate()));
                break;
            case 2:
                result = Objects.requireNonNull(timeFormat.parse(a.getStartTime())).
                        compareTo(timeFormat.parse(b.getStartTime()));
                break;
            case 3:
                result = a.getName().compareTo(b.getName());
                break;
            case 4:
                result = a.getQuantity() - b.getQuantity();
                break;
            case 5:
                result = (int) ((a.getDuration() - b.getDuration()) * 10);
                break;
        }
        return result * (isAscending ? 1 : -1);
    }

    private void filterZones(ArrayList<Zone> cloneList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = new Date();
        for (int i = cloneList.size() - 1; i >= 0; i--) {
            Zone current = cloneList.get(i);
            if (!current.getName().contains(zoneName) || !current.getLeader().contains(zoneLeader))
                cloneList.remove(current);
            else if (filterArray[0]) {
                if (!user.isJoinedZone(current.getId())) cloneList.remove(current);
            } else if (filterArray[1] && !current.getLeader().equals(user.getUsername()))
                cloneList.remove(current);
            else {
                try {
                    if (filterArray[2] &&
                            Objects.requireNonNull(dateFormat.
                                    parse(current.getClosedDate())).compareTo(date) < 0)
                        cloneList.remove(current);
                    else if (filterArray[3] &&
                            Objects.requireNonNull(dateFormat.
                                    parse(current.getStartDate())).compareTo(date) < 0)
                        cloneList.remove(current);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(String zoneName, String zoneLeader, boolean isAscending, int sortParam,
                        boolean[] filterArray) {
        this.zoneName = zoneName;
        this.zoneLeader = zoneLeader;
        this.isAscending = isAscending;
        this.sortParam = sortParam;
        this.filterArray = filterArray;
        resetZoneListFrag();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void resetZoneListFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        zoneListFrag = new ZoneListFrag(searchSortFilter(zoneArrayList.toArray()));
        ft.replace(R.id.home_frag_container, zoneListFrag);
        ft.commit();
    }

    private void resetUserInfoFrag() {
        int hosted = 0;
        for (Zone i : zoneArrayList)
            if (i.getLeader().equals(user.getUsername())) hosted++;
        UserInfoFrag userInfoFrag = new UserInfoFrag(hosted);
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft1.replace(R.id.home_frag_container, userInfoFrag);
        ft1.commit();
    }

    private void resetMapFrag() {
        HomeMapFrag zoneMapFrag = new HomeMapFrag();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft1.replace(R.id.home_frag_container, zoneMapFrag);
        ft1.commit();
    }

    public void editUser() {
        //TODO change to controller
        UserController.updateUser(user);
        resetUserInfoFrag();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            zoneArrayList = zoneDatabase.getAllZones();
            resetZoneListFrag();
        }
    }
}
