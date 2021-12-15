package com.android.asm2.controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.android.asm2.database.ReportDatabase;
import com.android.asm2.model.Report;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportController {
    private static final String MOCK_URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2";
    private static final String NG_URL = "https://s3749795-hoang-android-asm2.herokuapp.com/api";
    private static final String URL = NG_URL + "/reports";
    private static final long REFRESH_REQUEST = 60 * 1000;
    private static RequestQueue queue = null;
    private static ReportDatabase database = null;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = ReportDatabase.initAndGetInstance(context);
    }

    public static void getAllReports() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.handleData(new Gson().fromJson(object.toString(), Report.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshContent();
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }

    public static void addReport(Report report) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(report));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.addReport(report);
    }

    public static void updateReport(Report report) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(report));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.updateReport(report);
    }

    private static void refreshContent() {
        new CountDownTimer(REFRESH_REQUEST, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                getAllReports();
            }
        }.start();
    }
}
