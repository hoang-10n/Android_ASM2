package com.android.asm2.controller;

import android.content.Context;

import com.android.asm2.database.ReportDatabase;
import com.android.asm2.model.Report;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportController {
    private static final String URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2/reports";
    private static RequestQueue queue = null;
    private static ReportDatabase database = null;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = ReportDatabase.initAndGetInstance(context);
    }

    public static void getAllReports() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    database.clearData();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.addReport(new Gson().fromJson(object.toString(), Report.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }
}
