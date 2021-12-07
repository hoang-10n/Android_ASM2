package com.android.asm2;

import android.content.Context;
import android.util.Log;

import com.android.asm2.model.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UserController {
    private static final String URL = "https://my-json-server.typicode.com/hoang-10n/Android_ASM2/users";
    private static RequestQueue queue = null;
    private static UserDatabase database = null;

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
        database = new UserDatabase(context);
    }

    public static void getAllUsers() {
        JsonArrayRequest userArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    database.clearData();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            database.addUser(new Gson().fromJson(object.toString(), User.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Throwable::printStackTrace);
        queue.add(userArrayRequest);
    }
}
