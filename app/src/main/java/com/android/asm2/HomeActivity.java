package com.android.asm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.asm2.model.Zone;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ZoneDatabase database = new ZoneDatabase(this);
        ArrayList<Zone> zoneArrayList = database.getAllZones();
        Log.d("TAG", zoneArrayList.toString());
        ZoneAdapter adapter = new ZoneAdapter(this, zoneArrayList);
        ListView view = findViewById(R.id.test_adapter);
        view.setAdapter(adapter);
    }
}