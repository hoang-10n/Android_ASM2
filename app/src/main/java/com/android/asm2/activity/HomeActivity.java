package com.android.asm2.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.asm2.R;
import com.android.asm2.ZoneDialog;
import com.android.asm2.database.UserDatabase;
import com.android.asm2.database.ZoneDatabase;
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

        Intent intent = getIntent();
        String username = (String) intent.getExtras().get("username");

        Button popupBtn = findViewById(R.id.home_zone_list_popup_btn);
        ImageButton addBtn = findViewById(R.id.home_zone_list_add_btn);

        UserDatabase userDatabase = new UserDatabase(this);
        user = userDatabase.getUserByUsername(username);
        zoneDatabase = new ZoneDatabase(this);
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
            intent1.putExtra("leader", user.getUsername());
            intent1.putExtra("id", newId);
            intent1.putExtra("isAdded", true);
            startActivityForResult(intent1, 100);
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
                String[] joined = user.getJoinedZones();
                boolean isRemove = true;
                for (String zoneId : joined) {
                    if (current.getId().equals(zoneId)) {
                        isRemove = false;
                        break;
                    }
                }
                if (isRemove) cloneList.remove(current);
            } else if (filterArray[1] && !current.getLeader().equals(zoneLeader))
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
