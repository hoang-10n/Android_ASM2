package com.android.asm2.controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ZoneController {
    private static final String URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2/zones";
    private static RequestQueue queue = null;
    private static ZoneDatabase database = null;
    private static final long REFRESH_REQUEST = 60 * 1000;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = ZoneDatabase.initAndGetInstance(context);
    }

    public static void getAllZones() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.handleData(new Gson().fromJson(object.toString(), Zone.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshContent();
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }

    public static void addZone(Zone zone) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(zone));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.addZone(zone);
    }

    public static void updateZone(Zone zone) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(zone));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.updateZone(zone);
    }

    private static void refreshContent() {
        new CountDownTimer(REFRESH_REQUEST, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                getAllZones();
            }
        }.start();
    }
}
