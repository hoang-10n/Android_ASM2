package com.android.asm2.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.android.asm2.R;
import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.databinding.ActivityLocationPickerBinding;
import com.android.asm2.model.Zone;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationPickerActivity extends FragmentActivity implements OnMapReadyCallback {

    private ActivityLocationPickerBinding binding;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    private static final long UPDATE_INTERVAL = 10 * 1000; //10 sec
    private static final long FASTEST_INTERVAL = 2 * 1000; //2 sec
    private GoogleMap mMap;
    private ArrayList<Zone> zoneArrayList;
    private Zone chosenZone;
    private ZoneDatabase zoneDatabase;
    private Marker oldCurrentPosMarker, oldPlacingMarker;
    private Button saveBtn, discardBtn;
    protected FusedLocationProviderClient client;
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            onLocationChanged(locationResult.getLastLocation());
        }
    };

    public LocationPickerActivity() {
        zoneDatabase = ZoneDatabase.getInstance();
        zoneArrayList = zoneDatabase.getAllZones();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        discardBtn = findViewById(R.id.location_picker_discard_btn);
        saveBtn = findViewById(R.id.location_picker_save_btn);

        Intent intent = getIntent();
        String id = (String) intent.getExtras().get("id");
        chosenZone = zoneDatabase.getZoneById(id);

        discardBtn.setOnClickListener(v -> finish());
        saveBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ZoneInfoActivity.class);
            intent1.putExtra("id", chosenZone.getId());
            intent1.putExtra("newLat", oldPlacingMarker.getPosition().latitude);
            intent1.putExtra("newLong", oldPlacingMarker.getPosition().longitude);
            setResult(RESULT_OK, intent1);
            finish();
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        for (Zone zone : zoneArrayList) {
            LatLng pos = new LatLng(zone.getLatitude(), zone.getLongitude());
            String title = zone.getId() + ": " + zone.getName();
            if (zone.getId().equals(chosenZone.getId())) {
                mMap.addMarker(new MarkerOptions().position(pos).title(title)
                        .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_logo))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
            } else {
                mMap.addMarker(new MarkerOptions().position(pos).title("Current zone " + title)
                        .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_logo_simple))));
            }
        }
        mMap.setOnMapClickListener(latLng -> {
            saveBtn.setEnabled(true);
            if (oldPlacingMarker != null) oldPlacingMarker.remove();
            oldPlacingMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .title("New pos for " + chosenZone.getId())
                    .icon(BitmapDescriptorFactory.defaultMarker()));
        });
        startLocationUpdate();
    }

    private void onLocationChanged(Location lastLocation) {
        try {
            if (oldCurrentPosMarker != null) oldCurrentPosMarker.remove();
            LatLng newPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(newPosition).title("My location")
                    .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_current_pos)));
            oldCurrentPosMarker = mMap.addMarker(marker);
        } catch (IllegalStateException e) {
            stopLocationUpdate();
        }
    }

    private Bitmap getBitmap(int drawableRes) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_REQUEST_LOCATION);
    }

    private void stopLocationUpdate() {
        client.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        client.requestLocationUpdates(mLocationRequest, locationCallback, null);
    }
}