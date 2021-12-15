package com.android.asm2.controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.android.asm2.database.UserDatabase;
import com.android.asm2.model.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * User Controller to execute GET, POST and PUT requests to server and store user in User Database
 */
public class UserController {
    private static final String MOCK_URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2";
    private static final String NG_URL = "https://s3749795-hoang-android-asm2.herokuapp.com/api";
    private static final String URL = NG_URL + "/users";
    private static final long REFRESH_REQUEST = 60 * 1000; // Refresh content interval = 60 secs
    private static RequestQueue queue = null;
    private static UserDatabase database = null;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = UserDatabase.initAndGetInstance(context);
    }

    public static void getAllUsers() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.handleData(new Gson().fromJson(object.toString(), User.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshContent();
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }

    public static void addUser(User user) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(user));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.addUser(user);
    }

    public static void updateUser(User user) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(user));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonObject,
                    response -> Log.d("TAG", response.toString())
                    , Throwable::printStackTrace);
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.updateUser(user);
    }

    private static void refreshContent() {
        new CountDownTimer(REFRESH_REQUEST, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                getAllUsers();
            }
        }.start();
    }
}
