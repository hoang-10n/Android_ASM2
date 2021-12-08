package com.android.asm2.controller;

import android.content.Context;

import com.android.asm2.database.ZoneDatabase;
import com.android.asm2.model.Zone;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ZoneController {
    private static final String URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2/zones";
    private static RequestQueue queue = null;
    private static ZoneDatabase database = null;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = new ZoneDatabase(context);
    }

    public static void getAllZones() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    database.clearData();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.addZone(new Gson().fromJson(object.toString(), Zone.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }
}
